package com.vs.oneportfolio.main.presentaion.fixedAssets.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.fixedincome.history.MaturedFADao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FixedHistoryViewModel(
    private val maturedFADao: MaturedFADao
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(FixedHistoryState())
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
            initialValue = FixedHistoryState()
        )
    private fun loadData (){

        viewModelScope.launch {
            maturedFADao.getAllMaturedFA().collect { list->
                _state.update {
                    it.copy(
                        list = list
                    )

                }
            }
        }
    }


    fun onAction(action: FixedHistoryAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}