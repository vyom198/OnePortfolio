package com.vs.oneportfolio.core.AlarmManager

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.vs.oneportfolio.core.database.fixedincome.FixedIncomeEntity
import com.vs.oneportfolio.main.data.fixedAsset.reciever.FixedAssetReciever
import com.vs.oneportfolio.main.data.fixedAsset.reciever.OnPaymentReciever
import com.vs.oneportfolio.main.mapper.toCommaString
import timber.log.Timber

class PortfolioAlarmScheduler(
    private val context: Context
) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun schedule(item: FixedIncomeEntity) {
        val intent = Intent(context, FixedAssetReciever::class.java).apply {
            putExtra("id", item.id.toString())
            putExtra("name", item.depositName)
            putExtra("money", item.currentValue.toCommaString())
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
              item.dateMaturity,
            PendingIntent.getBroadcast(
                context,
                item.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )

    }


    override fun cancel(item: FixedIncomeEntity) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, FixedAssetReciever::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancelRepeating(item: FixedIncomeEntity) {
        val intent = Intent(context, OnPaymentReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 1000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel (pendingIntent)
    }

    override fun scheduleRepeating(
        item: FixedIncomeEntity,
        interestAmt : Double
    ) {
        Timber.i("setting repeating")
        val intent = Intent(context, OnPaymentReciever::class.java).apply {
            putExtra("id", item.id)
            putExtra("name", item.depositName)
            putExtra("interest", interestAmt)
            putExtra("compounding", item.isCumulative)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 1000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val intervalMs = item.payoutFrequencyMonths * 30 * 24 * 60 * 60 * 1000L
        val triggertime = item.dateOpened + intervalMs
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggertime,
            intervalMs,
            pendingIntent
        )
    }

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun scheduleRepeatingAfterNotification(
        item: FixedIncomeEntity,
        interestAmt: Double
    ) {
        val intent = Intent(context, OnPaymentReciever::class.java).apply {
            putExtra("id", item.id)
            putExtra("name", item.depositName)
            putExtra("interest", interestAmt)
            putExtra("compounding", item.isCumulative)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 2000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val triggertime =  System.currentTimeMillis() + 24 * 60 * 60 * 1000L
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggertime,
            pendingIntent
        )
    }

    override fun cancelRepeatingAfterNotification(item: FixedIncomeEntity) {
        val intent = Intent(context, OnPaymentReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 2000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel (pendingIntent)
    }
}