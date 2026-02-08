package com.vs.oneportfolio.core.AlarmManager

import com.vs.oneportfolio.core.database.fixedincome.FixedIncomeEntity
import com.vs.oneportfolio.core.database.realestate.RealEstateEntity
import com.vs.oneportfolio.main.presentaion.model.FixedAssetUI

interface AlarmScheduler {
    fun schedule(item: FixedIncomeEntity)
    fun cancel(item: FixedIncomeEntity)
    fun cancelRepeating (item : FixedIncomeEntity)
    fun scheduleRepeating(item:FixedIncomeEntity , interestAmt :Double)

    fun scheduleRepeatingAfterNotification(item:FixedIncomeEntity, interestAmt :Double)
    fun cancelRepeatingAfterNotification (item : FixedIncomeEntity)

    fun cancelRepeatingTax (item : RealEstateEntity)
    fun scheduleRepeatingTax(item: RealEstateEntity )

    fun cancelRepeatingRent (item : RealEstateEntity)
    fun scheduleRepeatingRent(item: RealEstateEntity )

    fun cancelRepeatingMortgage (item : RealEstateEntity)
    fun scheduleRepeatingMortgage(item: RealEstateEntity )

    fun scheduleRepeatingAfterNotificationRE(item:RealEstateEntity)
    fun cancelRepeatingAfterNotificationRE (item : RealEstateEntity)


    fun scheduleYield(item: RealEstateEntity)
    fun cancelYield(item : RealEstateEntity)

}