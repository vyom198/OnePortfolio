package com.vs.oneportfolio.main.data.realestate.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vs.oneportfolio.main.domain.realestate.notification.RENotification
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class OnMortgageReciever : BroadcastReceiver() , KoinComponent {
    private val reNotification : RENotification by inject()
    override fun onReceive(context: Context?, intent: Intent?) {
       reNotification.showOnMortgageConfirmation(
           id = intent?.getIntExtra("id" , 0) ?: return,
           name = intent.getStringExtra("name") ?: return,
           money = intent.getStringExtra("money") ?: return
       )
    }
}