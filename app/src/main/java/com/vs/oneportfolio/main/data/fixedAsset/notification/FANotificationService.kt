package com.vs.oneportfolio.main.data.fixedAsset.notification

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.ui.autofill.ContentType
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.vs.oneportfolio.R
import com.vs.oneportfolio.app.MainActivity
import com.vs.oneportfolio.main.data.fixedAsset.reciever.OnPaymentConfirmed
import com.vs.oneportfolio.main.data.fixedAsset.reciever.OnPaymentNotRecieved
import com.vs.oneportfolio.main.domain.fixedAsset.notification.FANotification
import com.vs.oneportfolio.main.mapper.toCommaString
import timber.log.Timber

class FANotificationService(
    private val context: Context
) : FANotification {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    override fun showMaturedNotification(
        id: String,
        name: String,
        money: String
    ) {

        Timber.i("entered showMaturedNotification")
        val intent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            id.hashCode() + 1 ,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, FA_CHANNEL_ID)
            .setSmallIcon(R.drawable.sack_dollar)
            .setContentTitle(name)
            .setContentText("your asset is matured now worth $${money}")
            .setContentIntent(activityPendingIntent)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(context).notify(id.hashCode(), notification)
            }
        } else {
            NotificationManagerCompat.from(context).notify(id.hashCode(), notification)
        }


    }

    override fun showOnPaymentConfirmation(
        id: Int,
        name: String,
        interest: Double,
        compounding: Boolean
    ) {
        val intent = Intent(context, OnPaymentConfirmed::class.java).apply {
            putExtra("id", id)
            putExtra("name", name)
            putExtra("interest", interest)


        }
        val intentNotConfirmed = Intent(context, OnPaymentNotRecieved::class.java).apply {
            putExtra("id", id)
            putExtra("name", name)
            putExtra("interest", interest)

        }
        val activityPendingIntent = PendingIntent.getBroadcast(
            context,
            id.hashCode() +2,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val activityPendingIntentNo = PendingIntent.getBroadcast(
            context,
            id.hashCode()+3,
            intentNotConfirmed,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, FA_CHANNEL_ID)
            .setSmallIcon(R.drawable.sack_dollar)
            .setContentTitle(name)
            .setContentText("Have you received payment of $${interest.toCommaString()} in your ${if (compounding) "Fixed Account" else "current Account"}?")
            .addAction(
                R.drawable.sack_dollar,
                "Yes",
                activityPendingIntent,

                ).addAction(
                R.drawable.sack_dollar,
                "No",
                activityPendingIntentNo

            ).setDeleteIntent(activityPendingIntentNo)
            .build()

        notificationManager.notify(id.hashCode()+2 , notification)
    }

    override fun cancelNotification(id: Int) {
        notificationManager.cancel(id)
    }

    companion object {
            const val FA_CHANNEL_ID = "fa_channel"
            const val FA_CHAANEL_NAME = "fa_channel_name"
        }
    }
