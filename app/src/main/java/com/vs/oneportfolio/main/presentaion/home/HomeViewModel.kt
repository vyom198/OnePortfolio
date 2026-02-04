package com.vs.oneportfolio.main.presentaion.home

 import androidx.lifecycle.ViewModel
 import androidx.lifecycle.viewModelScope
 import com.vs.oneportfolio.core.database.stocks.StockDao
 import kotlinx.coroutines.flow.MutableStateFlow
 import kotlinx.coroutines.flow.SharingStarted
 import kotlinx.coroutines.flow.collectLatest
 import kotlinx.coroutines.flow.onStart
 import kotlinx.coroutines.flow.stateIn
 import kotlinx.coroutines.flow.update
 import kotlinx.coroutines.launch


class HomeViewModel(
   private  val stockDao: StockDao
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if(!hasLoadedInitialData) {
                 gettradeCount()
                  loadPortfolioData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000L),
            initialValue = HomeState()
        )

    private fun gettradeCount (){
        viewModelScope.launch {
             stockDao.getCount().collect {count->
                _state.update {
                    it.copy(
                        totalItemsInTrade = count
                    )
                }
            }
        }
    }
    private fun loadPortfolioData(){
        viewModelScope.launch {
            stockDao.getTotalCurrentValue().collectLatest{ totalCurrentValue ->
               _state.update {
                   it.copy(
                       totalCurrentValue = totalCurrentValue
                   )
               }
            }

        }
        viewModelScope.launch {
            stockDao.getTotalInvested().collectLatest { totalInvested ->
                _state.update {
                    it.copy(
                        totalInvested = totalInvested
                    )
                }
            }
        }

    }

        fun onAction(action: HomeAction) {
            when(action) {

                else -> {}
            }
        }

}