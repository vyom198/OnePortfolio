package com.vs.oneportfolio.main.data.fixedAsset.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.vs.oneportfolio.core.database.fixedincome.FixedIcomeDao
import com.vs.oneportfolio.core.database.fixedincome.history.MaturedFADao
import com.vs.oneportfolio.main.domain.fixedAsset.notification.FANotification
import com.vs.oneportfolio.main.mapper.toMatured
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class FixedAssetReciever : BroadcastReceiver(), KoinComponent {

    private val faNotification : FANotification by inject()
    private  val  fixedIncomedao  : FixedIcomeDao by inject()
    private val scope : CoroutineScope by inject()
    private  val maturedFADao : MaturedFADao by inject()
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getStringExtra("id") ?: return
        val name = intent.getStringExtra("name") ?: return
        val money = intent.getStringExtra("money") ?: return
        faNotification.showMaturedNotification(
            id = id,
            name = name,
            money = money
        )
        val pendingResult = goAsync()

        scope.launch {
            try {
                val item = fixedIncomedao.getFixedIncomeById(id.toInt())
                item?.let {
                    // 1. Move to history
                    maturedFADao.insertMaturedFA(it.toMatured())
                    // 2. Delete from active
                    fixedIncomedao.deleteFixedIncomeById(id.toInt())
                }

            } finally {
                pendingResult.finish()
            }
        }


    }
}