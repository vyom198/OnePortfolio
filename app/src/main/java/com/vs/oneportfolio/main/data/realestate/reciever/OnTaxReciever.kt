package com.vs.oneportfolio.main.data.realestate.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vs.oneportfolio.main.domain.realestate.notification.RENotification
import com.vs.oneportfolio.main.mapper.formatLongToDate
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class OnTaxReciever : BroadcastReceiver(), KoinComponent {
    private val renotification : RENotification by inject()
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getIntExtra("id", -1) ?: return
        val name = intent.getStringExtra("name") ?: return
        val date = intent.getLongExtra("date", -1) ?: return
        val dateformat = formatLongToDate(date)
        renotification.showOnTaxPayment(
            id = id,
            name = name,
            date = dateformat
        )

    }
}