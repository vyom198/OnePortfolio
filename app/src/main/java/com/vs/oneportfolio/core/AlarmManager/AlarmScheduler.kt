package com.vs.oneportfolio.core.AlarmManager

import com.vs.oneportfolio.core.database.fixedincome.FixedIncomeEntity
import com.vs.oneportfolio.main.presentaion.model.FixedAssetUI

interface AlarmScheduler {
    fun schedule(item: FixedIncomeEntity)
    fun cancel(item: FixedIncomeEntity)
    fun cancelRepeating (item : FixedIncomeEntity)
    fun scheduleRepeating(item:FixedIncomeEntity , interestAmt :Double)

    fun scheduleRepeatingAfterNotification(item:FixedIncomeEntity, interestAmt :Double)
    fun cancelRepeatingAfterNotification (item : FixedIncomeEntity)
}