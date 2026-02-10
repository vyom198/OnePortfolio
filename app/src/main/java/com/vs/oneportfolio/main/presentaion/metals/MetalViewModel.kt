package com.vs.oneportfolio.main.presentaion.metals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.metals.MetalDao
import com.vs.oneportfolio.core.database.metals.history.SoldMetalDao
import com.vs.oneportfolio.core.database.metals.history.SoldMetalEntity
import com.vs.oneportfolio.main.presentaion.model.MetalUI
import com.vs.oneportfolio.main.presentaion.realestate.addrealEstate.AddEstateEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MetalViewModel(
    private val metalDao: MetalDao,
    private val soldMetalDao: SoldMetalDao
) : ViewModel() {

    private var hasLoadedInitialData = false
    private val eventChannel = Channel<MetalEvent>()
    val events = eventChannel.receiveAsFlow()
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
                        isAdding = false ,
                        currentMetal = null
                    )
                }

            }

            is MetalAction.onSaved -> {
                viewModelScope.launch {
                    val item = action.metal
                    metalDao.insertMetal(item)
                    _state.update {
                        it.copy(
                            isAdding = false ,
                            currentMetal = null
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

            MetalAction.OnDelete -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isAdding = false,
                            isDeleting = true
                        )
                    }
                }
            }
            MetalAction.OnSold -> {
               viewModelScope.launch {
                   val item = _state.value.currentMetal
                   val soldItem = SoldMetalEntity(
                       label = item!!.label,
                       weight = item.weight,
                       unit = item.unit,
                       karat = item.karat,
                       currentPrice = item.currentPrice
                   )
                   soldMetalDao.insertSoldMetal(soldItem)
                   metalDao.deleteMetalById(item!!.id)
                   eventChannel.send(MetalEvent.SoldEvent)
                   _state.update {
                       it.copy(
                           isAdding = false ,
                           currentMetal = null
                       )
                   }
               }
            }

            MetalAction.OnDeleteConfirm -> {
                viewModelScope.launch {
                    val item = _state.value.currentMetal
                    metalDao.deleteMetalById(item!!.id)
                    _state.update {
                        it.copy(
                            isDeleting = false ,
                            currentMetal = null
                        )
                    }
                }
            }

            MetalAction.OnCancelDelete -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                          isDeleting = false,
                            currentMetal = null
                        )
                    }
                }
            }
        }
    }

}