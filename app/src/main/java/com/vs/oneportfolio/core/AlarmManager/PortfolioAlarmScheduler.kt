package com.vs.oneportfolio.core.AlarmManager

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.vs.oneportfolio.core.database.fixedincome.FixedIncomeEntity
import com.vs.oneportfolio.core.database.realestate.RealEstateEntity
import com.vs.oneportfolio.main.data.fixedAsset.reciever.FixedAssetReciever
import com.vs.oneportfolio.main.data.fixedAsset.reciever.OnPaymentReciever
import com.vs.oneportfolio.main.data.realestate.reciever.OnMortgageReciever
import com.vs.oneportfolio.main.data.realestate.reciever.OnRentReciever
import com.vs.oneportfolio.main.data.realestate.reciever.OnTaxReciever
import com.vs.oneportfolio.main.data.realestate.reciever.OnYieldReciever
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



    override fun scheduleRepeatingTax(item: RealEstateEntity) {
        val intent = Intent(context, OnTaxReciever::class.java).apply {
            putExtra("id", item.id)
            putExtra("name", item.propertyName)
            putExtra("date", item.taxDueDate)

        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 3000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val intervalMs = 12 * 30 * 24 * 60 * 60 * 1000L
        val triggerTime = item.taxDueDate - (7* 24 * 60 * 60 * 1000L)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            intervalMs,
            pendingIntent
        )
    }
    override fun cancelRepeatingTax(item: RealEstateEntity) {
        val intent = Intent(context, OnTaxReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 3000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel (pendingIntent)
    }

    override fun scheduleRepeatingRent(item: RealEstateEntity) {
        val intent = Intent(context, OnRentReciever::class.java).apply {
            putExtra("id", item.id)
            putExtra("name", item.propertyName)
            putExtra("money", item.monthlyRent)

        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 4000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val intervalMs =  30 * 24 * 60 * 60 * 1000L
        val triggerTime = item.purchaseDate + intervalMs
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            intervalMs,
            pendingIntent
        )
    }

    override fun cancelRepeatingRent(item: RealEstateEntity) {
        val intent = Intent(context, OnRentReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 4000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel (pendingIntent)
    }




    override fun scheduleRepeatingMortgage(item: RealEstateEntity) {
        val intent = Intent(context, OnMortgageReciever::class.java).apply {
            putExtra("id", item.id)
            putExtra("name", item.propertyName)
            putExtra("money", item.mortgagePayment?.toCommaString())

        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 5000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val intervalMs =  30 * 24 * 60 * 60 * 1000L
        val triggerTime = item.purchaseDate + intervalMs
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            intervalMs,
            pendingIntent
        )
    }
    override fun cancelRepeatingMortgage(item: RealEstateEntity) {
        val intent = Intent(context, OnMortgageReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 5000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel (pendingIntent)
    }

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun scheduleRepeatingAfterNotificationRE(item: RealEstateEntity) {
        val intent = Intent(context, OnMortgageReciever::class.java).apply {
            putExtra("id", item.id)
            putExtra("name", item.propertyName)
            putExtra("money", item.mortgagePayment?.toCommaString())

        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 6000,
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

    override fun cancelRepeatingAfterNotificationRE(item: RealEstateEntity) {
        val intent = Intent(context, OnMortgageReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 6000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel (pendingIntent)
    }

    override fun scheduleYield(item: RealEstateEntity) {
        val intent = Intent(context, OnYieldReciever::class.java).apply {
            putExtra("id", item.id)
            putExtra("name", item.propertyName)
            putExtra("oldmoney", item.purchasePrice)
            putExtra("newmoney", item.purchasePrice +(item.yieldRate * item.purchasePrice*0.01))
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 7000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val intervalMs =  12*30 * 24 * 60 * 60 * 1000L
        val triggertime = item.purchaseDate + intervalMs
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggertime,
                       intervalMs,
            pendingIntent
        )
    }

    override fun cancelYield(item: RealEstateEntity) {
        val intent = Intent(context, OnYieldReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode() + 7000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel (pendingIntent)
    }


}