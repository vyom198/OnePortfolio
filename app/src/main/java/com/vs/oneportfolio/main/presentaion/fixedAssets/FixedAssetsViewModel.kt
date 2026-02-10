package com.vs.oneportfolio.main.presentaion.fixedAssets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.AlarmManager.AlarmScheduler
import com.vs.oneportfolio.core.database.fixedincome.FixedIcomeDao
import com.vs.oneportfolio.core.database.fixedincome.FixedIncomeEntity
import com.vs.oneportfolio.main.mapper.toEntity
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.model.getPayOutType
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.model.interestBasedOnPayoutType
import com.vs.oneportfolio.main.presentaion.model.FixedAssetUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FixedAssetsViewModel(
    private val fixedIcomeDao: FixedIcomeDao,
    private val alarmManager: AlarmScheduler,
    private val scope : CoroutineScope
) : ViewModel() {

    private var hasLoadedInitialData = false
    private val eventChannel = Channel<FixedAssetEvent>()
    val events =  eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(FixedAssetsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
               loadData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = FixedAssetsState()
        )
    private fun loadData (){
        viewModelScope.launch {
          fixedIcomeDao.getAllFixedIncome().collect{ list->
              _state.update {
                  it.copy(
                      fixedAssets = list.map {
                          FixedAssetUI(
                              id = it.id,
                              depositName = it.depositName,
                              InstitutionName = it.InstitutionName,
                              amtPrincipal = it.amtPrincipal,
                              interestRatePercent = it.interestRatePercent,
                              notifyOnMaturity = it.notifyOnMaturity,
                              notifyOnInterestCredit = it.notifyOnInterestCredit,
                              currentValue = it.currentValue,
                              payoutFrequencyMonths = it.payoutFrequencyMonths.getPayOutType(),
                              dateOpened = it.dateOpened,
                              dateMaturity = it.dateMaturity
                          )
                      }
                  )
              }
          }
        }
    }
    private fun notifyPayment(enabled : Boolean , item : FixedAssetUI){
        viewModelScope.launch {
            fixedIcomeDao.updatePaymentNotify(item.id , enabled)
            val newItem = fixedIcomeDao.getFixedIncomeById(item.id)!!
            if(enabled){
                val interestAmt = (newItem.amtPrincipal * newItem.interestRatePercent)/100
                val interestRecieved = interestBasedOnPayoutType(interestAmt, item.payoutFrequencyMonths)
                alarmManager.scheduleRepeating(item = newItem , interestRecieved)
            }else{
                alarmManager.cancelRepeating(
                    item = newItem
                )
            }

        }
    }
    private fun  notifyMaturity(enabled : Boolean, item : FixedAssetUI ){
        viewModelScope.launch {
            fixedIcomeDao.updateMaturityNotify(item.id , enabled)
            val newItem = fixedIcomeDao.getFixedIncomeById(item.id)!!
            if(enabled){
                alarmManager.schedule(item = newItem)
            }else{
                alarmManager.cancel(
                    item = newItem
                )
            }

        }
    }
    fun onAction(action: FixedAssetsAction) {
        when (action) {
            FixedAssetsAction.onAdding -> {
               _state.update {
                   it.copy(
                       isAdding = true
                   )
               }
            }
            FixedAssetsAction.onDismiss -> {
                _state.update {
                    it.copy(
                        isAdding = false
                    )
                }
            }

            is FixedAssetsAction.onSaved -> {
                viewModelScope.launch {
                    fixedIcomeDao.insertFixedIncome(action.item)
                    _state.update {
                        it.copy(
                            isAdding = false
                        )
                    }
                }
            }

            is FixedAssetsAction.onNotifyMaturity -> notifyMaturity(action.enabled, action.item)
            is FixedAssetsAction.onNotifyPayment -> notifyPayment(action.enabled, action.item)
            FixedAssetsAction.OnCancelDelete -> {
                _state.update {
                    it.copy(
                        isDeleting = false,
                        currentIem = null
                    )
                }
            }
            is FixedAssetsAction.OnDelete -> {
                _state.update {
                    it.copy(
                        isDeleting = true,
                        currentIem = action.item
                    )
                }
            }
          FixedAssetsAction.OnDeleteConfirm -> {
                viewModelScope.launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main){
                        _state.update {
                            it.copy(
                                isDeleting = false
                            )
                        }
                    }
                    val item = fixedIcomeDao.getFixedIncomeById(_state.value.currentIem!!.id)!!
                    deleteAsynchronous(item)
                    fixedIcomeDao.deleteFixedIncomeById(item.id)
                    withContext(Dispatchers.Main){
                        eventChannel.send(FixedAssetEvent.onDelete)
                    }

                }
            }
        }
        }

    private fun deleteAsynchronous (item : FixedIncomeEntity){
        scope.launch(Dispatchers.Default) {
            try {

                val jobs = mutableListOf<Job>()

                if (item.notifyOnMaturity) {
                    jobs.add(launch {  alarmManager.cancel(item) })
                }
                if (item.notifyOnInterestCredit) {
                    jobs.add(launch {  alarmManager.cancelRepeating(item) })
                }

                jobs.joinAll()
            }catch (e : Exception){
                e.printStackTrace()
            }

        }
    }
}

