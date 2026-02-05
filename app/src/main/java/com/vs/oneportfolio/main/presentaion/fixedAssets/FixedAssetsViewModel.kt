package com.vs.oneportfolio.main.presentaion.fixedAssets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class FixedAssetsViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(FixedAssetsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = FixedAssetsState()
        )

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

            }
        }
    }

