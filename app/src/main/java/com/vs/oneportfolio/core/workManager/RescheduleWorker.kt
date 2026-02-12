package com.vs.oneportfolio.core.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vs.oneportfolio.core.AlarmManager.AlarmScheduler
import com.vs.oneportfolio.core.database.fixedincome.FixedIcomeDao
import com.vs.oneportfolio.core.database.realestate.RealEstateDao

class RescheduleWorker(
    context: Context,
    params: WorkerParameters,
    private val fixedAssetdao: FixedIcomeDao,
    private val realdao: RealEstateDao,
    private val alarmScheduler: AlarmScheduler
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            // 1. Handle Fixed Income Assets
            fixedAssetdao.getAllFixedIncomeSnap().forEach { item ->
                if (item.notifyOnMaturity) {
                    alarmScheduler.schedule(item)
                }
                if (item.notifyOnInterestCredit) {
                    val amt = item.amtPrincipal * (item.interestRatePercent) * 0.01
                    alarmScheduler.scheduleRepeating(item, amt)
                }
            }

            // 2. Handle Real Estate Assets
            realdao.getAllRealEstatesSnap().forEach { item ->
                alarmScheduler.scheduleYield(item)
                if (item.rentReminder) alarmScheduler.scheduleRepeatingRent(item)
                if (item.mortgageReminder) alarmScheduler.scheduleRepeatingMortgage(item)
                if (item.taxReminder) alarmScheduler.scheduleRepeatingTax(item)
            }

            Result.success()
        } catch (e: Exception) {
            // If something fails (e.g. DB error), you can tell WorkManager to retry later
            Result.retry()
        }
    }
}