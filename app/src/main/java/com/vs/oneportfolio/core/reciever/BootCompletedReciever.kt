package com.vs.oneportfolio.core.reciever

import android.R.attr.action
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.vs.oneportfolio.core.workManager.RescheduleWorker
import org.koin.core.component.KoinComponent

class BootCompletedReciever : BroadcastReceiver() , KoinComponent{
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val workRequest = OneTimeWorkRequestBuilder<RescheduleWorker>()
                .setConstraints(androidx.work.Constraints.Builder()
                    .setRequiresBatteryNotLow(true) // Optional: only run if battery is okay
                    .build())
                .build()

            WorkManager.getInstance(context!!).enqueue(workRequest)
        }
    }
}