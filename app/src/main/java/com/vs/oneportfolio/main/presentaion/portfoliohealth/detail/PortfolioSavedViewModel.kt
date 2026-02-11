package com.vs.oneportfolio.main.presentaion.portfoliohealth.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.vs.oneportfolio.app.navigation.AppRoute
import com.vs.oneportfolio.core.database.portfoliohealth.PortfolioHealthDao
import com.vs.oneportfolio.main.mapper.toUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PortfolioSavedViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val portfolioHealthDao: PortfolioHealthDao
) : ViewModel() {

    private var hasLoadedInitialData = false
    private val id = savedStateHandle.toRoute<AppRoute.PortfolioSaved>().id
    private val _state = MutableStateFlow(PortfolioSavedState())
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
            initialValue = PortfolioSavedState()
        )
    private fun loadData(){
        viewModelScope.launch {
            val item = portfolioHealthDao.getPortfolioAnalysisById(id)
            val itemUI = item!!.toUI()
            _state.update {
                it.copy(
                    item = itemUI
                )
            }
        }
    }
    fun onAction(action: PortfolioSavedAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}