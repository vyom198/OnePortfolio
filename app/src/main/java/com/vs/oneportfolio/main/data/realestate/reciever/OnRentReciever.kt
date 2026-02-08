package com.vs.oneportfolio.main.data.realestate.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vs.oneportfolio.main.domain.realestate.notification.RENotification
import com.vs.oneportfolio.main.mapper.toCommaString
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OnRentReciever : BroadcastReceiver() , KoinComponent {
    private  val reNotification : RENotification by inject()
    override fun onReceive(context: Context?, intent: Intent) {
        val name = intent.getStringExtra("name") ?: return
        val id = intent.getIntExtra("id", -1) ?: return
        val money = intent.getDoubleExtra("money", -1.0) ?: return
        reNotification.showRentalNotification(
            id = id.toString(),
            name = name,
            money = money.toCommaString()
        )
    }
}