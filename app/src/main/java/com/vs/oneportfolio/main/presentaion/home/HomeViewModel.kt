package com.vs.oneportfolio.main.presentaion.home

 import androidx.lifecycle.ViewModel
 import androidx.lifecycle.viewModelScope
 import com.vs.oneportfolio.core.database.crypto.CryptoDao
 import com.vs.oneportfolio.core.database.fixedincome.FixedIcomeDao
 import com.vs.oneportfolio.core.database.metals.MetalDao
 import com.vs.oneportfolio.core.database.realestate.RealEstateDao
 import com.vs.oneportfolio.core.database.stocks.StockDao
 import kotlinx.coroutines.flow.MutableStateFlow
 import kotlinx.coroutines.flow.SharingStarted
 import kotlinx.coroutines.flow.collectLatest
 import kotlinx.coroutines.flow.onStart
 import kotlinx.coroutines.flow.stateIn
 import kotlinx.coroutines.flow.update
 import kotlinx.coroutines.launch


class HomeViewModel(
   private  val stockDao: StockDao,
    private val cryptoDao: CryptoDao,
    private val fixedIcomeDao: FixedIcomeDao,
    private  val realEstateDao: RealEstateDao,
    private val metalDao : MetalDao
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
        viewModelScope.launch {
            cryptoDao.getCount().collect { count ->
                _state.update {
                    it.copy(
                        totalItemsInCrypto = count
                    )
                }
            }
        }
        viewModelScope.launch {
            fixedIcomeDao.getCount().collect {
                count ->
                _state.update {
                    it.copy(
                        totalItemsinFA = count
                    )
                }
            }
        }
        viewModelScope.launch {
            realEstateDao.getCount().collect {
                count ->
                _state.update {
                    it.copy(
                        totalItemsInRealEstate = count
                    )
                }

            }
        }
        viewModelScope.launch {
            metalDao.getCount().collect {
                count ->
                _state.update {
                    it.copy(
                        totalItemsInMetals = count
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

        viewModelScope.launch {
            cryptoDao.getTotalCurrentValue().collectLatest {
                totalCurrentValue ->
                _state.update {
                    it.copy(
                      totalCurrentValueOfCrypto = totalCurrentValue
                    )
                }
            }
        }

        viewModelScope.launch {
            cryptoDao.getTotalInvested().collectLatest { totalInvested ->
                _state.update {
                    it.copy(totalInvestedInCrypto = totalInvested)

                }

            }
        }
        viewModelScope.launch {
            fixedIcomeDao.getTotalInvested().collect {
                totalInvested ->
                _state.update {
                    it.copy(
                        totalInvestedInFA = totalInvested
                    )
                }
            }
            }

        viewModelScope.launch {
            fixedIcomeDao.getCurrentValue().collect { totalCurrentValue ->
                _state.update {
                    it.copy(
                        totalCurrentValueInFA = totalCurrentValue
                    )
                }
            }
        }
        viewModelScope.launch {
            realEstateDao.getTotalCurrentValue().collect {
                 currentValue->
                _state.update {
                    it.copy(
                        totalCurrentValueInRealEstate = currentValue
                    )
                }

            }

        }
        viewModelScope.launch {
            realEstateDao.getTotalInvested().collect {
                totalInvested ->
                _state.update {
                    it.copy(
                        totalInvestedInRealEstate = totalInvested
                    )

                }
            }


        }

        viewModelScope.launch {
            metalDao.gettotalCurrentValue().collect {
                totalCurrentValue ->
                _state.update {
                    it.copy(
                        totalCurrentValueOfMetals = totalCurrentValue
                    )
                }
            }
        }
        viewModelScope.launch {
            metalDao.getTotalInvestedValue().collect {
                    totalInvested ->
                _state.update {
                    it.copy(
                        totalInvestedInMetals = totalInvested
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