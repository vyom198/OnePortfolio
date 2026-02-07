package com.vs.oneportfolio.main.data.fixedAsset.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vs.oneportfolio.core.AlarmManager.AlarmScheduler
import com.vs.oneportfolio.core.database.fixedincome.FixedIcomeDao
import com.vs.oneportfolio.main.domain.fixedAsset.notification.FANotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class OnPaymentNotRecieved : BroadcastReceiver() , KoinComponent {
    private val scope : CoroutineScope by inject()
    private val alarmManager : AlarmScheduler by inject()
    private val fixedIcomeDao : FixedIcomeDao by inject()
    private val faNotification : FANotification by inject()
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getIntExtra("id" , 0) ?: return
        val interest = intent.getDoubleExtra("interest" , 0.0)

        val pendingResult = goAsync()
        scope.launch {
            try {
                val item = fixedIcomeDao.getFixedIncomeById(id)
                item?.let { alarmManager.scheduleRepeatingAfterNotification(it, interest) }
                faNotification.cancelNotification(id.hashCode() + 2)
            } finally {
                pendingResult.finish()
            }
        }


    }
}