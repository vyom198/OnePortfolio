package com.vs.oneportfolio.main.presentaion

import com.vs.oneportfolio.core.database.stocks.StocksEntity
import com.vs.oneportfolio.core.gemini.StockTransaction

data class HomeState(
   val text : String = "" ,
   val loading : Boolean = false ,
   val stocksList : List<StocksEntity> = emptyList()
)