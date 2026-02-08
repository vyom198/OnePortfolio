package com.vs.oneportfolio.main.presentaion.realestate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.AlarmManager.AlarmScheduler
import com.vs.oneportfolio.core.database.realestate.RealEstateDao
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.model.interestBasedOnPayoutType
import com.vs.oneportfolio.main.presentaion.model.FixedAssetUI
import com.vs.oneportfolio.main.presentaion.model.RealEstateUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RealViewModel(
    private val realEstateDao: RealEstateDao,
    private val alarmManager: AlarmScheduler
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(RealState())
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
            initialValue = RealState()
        )

    private  fun loadData(){
        viewModelScope.launch {
            realEstateDao.getAllRealEstates().collect{ items->
                _state.update {
                    it.copy(
                        realestates = items.map {
                            RealEstateUI(
                                id = it.id,
                                propertyName = it.propertyName,
                                propertyType = it.propertyType,
                                purchaseDate = it.purchaseDate,
                                purchasePrice = it.purchasePrice,
                                currentMarketValue = it.currentMarketValue,
                                yieldRate = it.yieldRate,
                                address = it.address?:"",
                                properImg = it.properImg,
                                isRented = it.isRented,
                                rentReminder = it.rentReminder,
                                hasMortgage = it.hasMortgage,
                                mortgageReminder = it.mortgageReminder,
                                taxReminder = it.taxReminder,
                                mortgageBalance = it.mortgageBalance,
                                taxDueDate = it.taxDueDate,
                                mortgagePayment = it.mortgagePayment,
                                monthlyRent = it.monthlyRent,

                            )
                        }
                    )

                }
            }
        }
    }

    private fun notifyRental(enabled : Boolean , item : RealEstateUI){
        viewModelScope.launch {
            realEstateDao.updateRentNotify(item.id , enabled)
            val newItem = realEstateDao.getRealEstateById(item.id)!!
            if(enabled){
                 alarmManager.scheduleRepeatingRent(newItem)
            }else{
                alarmManager.cancelRepeatingRent(newItem)
            }

        }
    }
    private fun notifyTax(enabled : Boolean , item : RealEstateUI){
        viewModelScope.launch {
            realEstateDao.updateTaxNotify(item.id , enabled)
            val newItem = realEstateDao.getRealEstateById(item.id)!!
            if(enabled){
                alarmManager.scheduleRepeatingTax(newItem)
            }else{
                alarmManager.cancelRepeatingTax(newItem)
            }

        }
    }
    private fun notifyMortgage(enabled : Boolean , item : RealEstateUI){
        viewModelScope.launch {
            realEstateDao.updateMortgageNotify(item.id , enabled)
            val newItem = realEstateDao.getRealEstateById(item.id)!!
            if(enabled){
                alarmManager.scheduleRepeatingMortgage(newItem)
            }else{
                alarmManager.cancelRepeatingMortgage(newItem)
            }

        }
    }


    fun onAction(action: RealAction) {
        when (action) {
            is RealAction.onNotifyMortgage -> notifyMortgage(action.enabled, action.item)
            is RealAction.onNotifyRental -> notifyRental(action.enabled, action.item)
            is RealAction.onNotifyTax -> notifyTax(action.enabled, action.item)
        }
    }

}