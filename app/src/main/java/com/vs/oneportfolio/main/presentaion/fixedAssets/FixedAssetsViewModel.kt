package com.vs.oneportfolio.main.presentaion.fixedAssets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vs.oneportfolio.core.database.fixedincome.FixedIcomeDao
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.model.getPayOutType
import com.vs.oneportfolio.main.presentaion.model.FixedAssetUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FixedAssetsViewModel(
    private val fixedIcomeDao: FixedIcomeDao
) : ViewModel() {

    private var hasLoadedInitialData = false

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
        }
        }
    }

