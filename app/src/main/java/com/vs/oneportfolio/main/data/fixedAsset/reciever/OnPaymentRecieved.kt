package com.vs.oneportfolio.main.data.fixedAsset.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vs.oneportfolio.main.domain.fixedAsset.notification.FANotification
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OnPaymentReciever : BroadcastReceiver() , KoinComponent {
     private val faNotification : FANotification by inject()
     override fun onReceive(context: Context?, intent: Intent?) {
         val id = intent?.getIntExtra("id" , 0) ?: return
         val name = intent.getStringExtra("name") ?: return
         val interest = intent.getDoubleExtra("interest" , 0.0)
         val compounding = intent.getBooleanExtra("compounding" , false)
         faNotification.showOnPaymentConfirmation(
             id = id,
             name = name,
             interest = interest,
             compounding = compounding
         )
    }
}