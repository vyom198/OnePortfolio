package com.vs.oneportfolio.main.data.realestate.notification

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.vs.oneportfolio.R
import com.vs.oneportfolio.app.MainActivity
import com.vs.oneportfolio.main.data.realestate.reciever.OnMortgageConfirmed
import com.vs.oneportfolio.main.data.realestate.reciever.OnMortgageNotConfirmed
import com.vs.oneportfolio.main.domain.realestate.notification.RENotification

class RENotificationSerivice(
    private val context: Context
) : RENotification {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    override fun showOnYield(
        id: String,
        name: String,
        oldMoney: String,
        newMoney: String
    ) {
        val intent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            id.hashCode() + 7 ,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, RE_CHANNEL_ID)
            .setSmallIcon(R.drawable.sack_dollar)
            .setContentTitle(name)
            .setContentText("Congrats! Now your property is worth increased from $oldMoney to $newMoney")
            .setContentIntent(activityPendingIntent)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(context).notify(id.hashCode()+7, notification)
            }
        } else {
            NotificationManagerCompat.from(context).notify(id.hashCode()+7, notification)
        }
    }

    override fun showRentalNotification(
        id: String,
        name: String,
        money: String
    ) {
        val intent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            id.hashCode() + 4 ,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, RE_CHANNEL_ID)
            .setSmallIcon(R.drawable.sack_dollar)
            .setContentTitle(name)
            .setContentText("collect your rent of $money")
            .setContentIntent(activityPendingIntent)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(context).notify(id.hashCode()+4, notification)
            }
        } else {
            NotificationManagerCompat.from(context).notify(id.hashCode()+4, notification)
        }
    }

    override fun showOnMortgageConfirmation(
        id: Int,
        name: String,
        money: String
    ) {
        val intent = Intent(context, OnMortgageConfirmed::class.java).apply {
            putExtra("id", id)
            putExtra("name", name)
        }
        val intentNotConfirmed = Intent(context, OnMortgageNotConfirmed::class.java).apply {
            putExtra("id", id)
            putExtra("name", name)
        }
        val activityPendingIntent = PendingIntent.getBroadcast(
            context,
            id.hashCode() +5,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val activityPendingIntentNo = PendingIntent.getBroadcast(
            context,
            id.hashCode()+6,
            intentNotConfirmed,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, RE_CHANNEL_ID)
            .setSmallIcon(R.drawable.sack_dollar)
            .setContentTitle(name)
            .setContentText("Have you paid your mortgage of $money ?")
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

        notificationManager.notify(id.hashCode()+5 , notification)
    }

    override fun showOnTaxPayment(id: Int, name: String, date: String) {

        val intent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            id.hashCode() + 3 ,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, RE_CHANNEL_ID)
            .setSmallIcon(R.drawable.sack_dollar)
            .setContentTitle(name)
            .setContentText("Pay your taxes before $date to avoid extra charges")
            .setContentIntent(activityPendingIntent)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(context).notify(id.hashCode()+3, notification)
            }
        } else {
            NotificationManagerCompat.from(context).notify(id.hashCode()+3, notification)
        }


    }

    override fun cancelNotification(id: Int) {
        notificationManager.cancel(id)
    }

    companion object {
        const val RE_CHANNEL_ID = "re_channel"
        const val RE_CHANNEL_NAME = "re_channel_name"
    }
}