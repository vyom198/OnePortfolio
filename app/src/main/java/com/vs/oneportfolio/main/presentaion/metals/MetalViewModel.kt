package com.vs.oneportfolio.main.presentaion.metals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.metals.MetalDao
import com.vs.oneportfolio.main.presentaion.model.MetalUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MetalViewModel(
    private val metalDao: MetalDao
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MetalState())
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
            initialValue = MetalState()
        )
    private fun loadData(){
        viewModelScope.launch {
            metalDao.getAllMetals().collect {  metals->
                _state.update {
                    it.copy(
                        metalList = metals.map {
                            MetalUI(
                                id = it.id,
                                label = it.label,
                                weight = it.weight,
                                unit = it.unit,
                                karat = it.karat,
                                currentPrice = it.currentPrice,
                                purchasePrice = it.purchasePrice,
                                purchaseDate = it.purchaseDate,
                                storageLocation = it.storageLocation


                            )

                        }
                    )
                }

            }
        }
    }

    fun onAction(action: MetalAction) {
        when (action) {
            MetalAction.onAdding -> {
                _state.update {
                    it.copy(
                        isAdding = true
                    )
                }
            }

            MetalAction.onDismiss -> {
                _state.update {
                    it.copy(
                        isAdding = false
                    )
                }

            }

            is MetalAction.onSaved -> {
                viewModelScope.launch {
                    val item = action.metal
                    metalDao.insertMetal(item)
                    _state.update {
                        it.copy(
                            isAdding = false
                        )
                    }
                }
            }

            is MetalAction.onEdit -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            currentMetal = action.metal,
                            isAdding = true
                        )

                    }
                }
            }
        }
    }

}