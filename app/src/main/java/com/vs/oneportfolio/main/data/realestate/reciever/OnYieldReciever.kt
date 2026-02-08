package com.vs.oneportfolio.main.data.realestate.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vs.oneportfolio.core.database.realestate.RealEstateDao
import com.vs.oneportfolio.main.domain.realestate.notification.RENotification
import com.vs.oneportfolio.main.mapper.toCommaString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import kotlin.getValue

class OnYieldReciever : BroadcastReceiver() , KoinComponent {
    private  val reNotification : RENotification by inject()
    private val scope : CoroutineScope by inject()
    private val realEstateDao : RealEstateDao by inject()
    override fun onReceive(context: Context?, intent: Intent) {
        val name = intent.getStringExtra("name") ?: return
        val id = intent.getIntExtra("id", -1)
        val oldmoney = intent.getDoubleExtra("oldmoney", 0.0) ?: return
        val newmoney = intent.getDoubleExtra("newmoney", 0.0) ?: return
        Timber.i(id.toString() + newmoney.toString())
        reNotification.showOnYield(
            id = id.toString(),
            name = name,
            oldMoney = oldmoney.toCommaString(),
            newMoney = newmoney.toCommaString()
        )
        val pendingResult = goAsync()
        scope.launch {
            try {
                realEstateDao.updateCurrebtBalance(id , newmoney)
            }finally {
                pendingResult.finish()
            }
        }
    }
}