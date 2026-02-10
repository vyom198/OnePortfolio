package com.vs.oneportfolio.main.presentaion.metals.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.metals.MetalDao
import com.vs.oneportfolio.core.database.metals.history.SoldMetalDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SoldMetalViewModel(
    private val soldMetalDao: SoldMetalDao
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SoldMetalState())
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
            initialValue = SoldMetalState()
        )
    private  fun loadData(){
        viewModelScope.launch {
            soldMetalDao.getAllSoldMetals().collect { list->
                _state.update {
                    it.copy(
                        list = list
                    )
                }
            }
        }
    }
    fun onAction(action: SoldMetalAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}