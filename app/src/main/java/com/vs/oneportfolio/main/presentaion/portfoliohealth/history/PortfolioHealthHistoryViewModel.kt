package com.vs.oneportfolio.main.presentaion.portfoliohealth.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.portfoliohealth.PortfolioHealthDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PortfolioHealthHistoryViewModel(
    private val portfolioHealthDao: PortfolioHealthDao
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(PortfolioHealthHistoryState())
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
            initialValue = PortfolioHealthHistoryState()
        )
    private fun loadData (){
        viewModelScope.launch {
            portfolioHealthDao.getAllPortfolioAnalyses().collect{ list ->
                _state.update {
                    it.copy(
                        data = list
                    )

                }
            }
        }
    }
    fun onAction(action: PortfolioHealthHistoryAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}