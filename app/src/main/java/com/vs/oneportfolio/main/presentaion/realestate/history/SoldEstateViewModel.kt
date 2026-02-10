package com.vs.oneportfolio.main.presentaion.realestate.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.realestate.history.SoldEstateDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SoldEstateViewModel(
    private val soldEstateDao: SoldEstateDao
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SoldEstateState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadInitialData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SoldEstateState()
        )
   private  fun loadInitialData() {
       viewModelScope.launch {
           soldEstateDao.getAllSoldEstates().collect { estateEntities ->
               _state.update {
                   it.copy(
                       list = estateEntities
                   )
               }
           }
       }
   }
    fun onAction(action: SoldEstateAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}