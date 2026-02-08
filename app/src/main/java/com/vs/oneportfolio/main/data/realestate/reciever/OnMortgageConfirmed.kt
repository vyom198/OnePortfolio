package com.vs.oneportfolio.main.data.realestate.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vs.oneportfolio.core.AlarmManager.AlarmScheduler
import com.vs.oneportfolio.core.database.realestate.RealEstateDao
import com.vs.oneportfolio.main.domain.realestate.notification.RENotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OnMortgageConfirmed : BroadcastReceiver() , KoinComponent {
    private val realStateDao : RealEstateDao by inject()
    private val alarmManager : AlarmScheduler by inject()
    private val reNotification : RENotification by inject()
    private val scope : CoroutineScope by inject()
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getIntExtra("id" , 0) ?: return
        reNotification.cancelNotification(id.hashCode()+5)
        val pendingResult = goAsync()
        scope.launch {
            try {
                val item = realStateDao.getRealEstateById(id)

                item?.let{ item->
                        val new =   item.copy(
                          mortgageBalance = item.mortgageBalance?.minus(item.mortgagePayment?:0.0)
                        )
                        realStateDao.insertRealEstate(new)


                    alarmManager.cancelRepeatingAfterNotificationRE(item)

                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}