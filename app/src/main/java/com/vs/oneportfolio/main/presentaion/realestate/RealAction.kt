package com.vs.oneportfolio.main.presentaion.realestate

import com.vs.oneportfolio.main.presentaion.fixedAssets.FixedAssetsAction
import com.vs.oneportfolio.main.presentaion.model.FixedAssetUI
import com.vs.oneportfolio.main.presentaion.model.RealEstateUI

sealed interface RealAction {
    data class onNotifyTax(val enabled : Boolean , val item : RealEstateUI) : RealAction
    data class onNotifyRental(val enabled : Boolean , val item : RealEstateUI) : RealAction
    data class onNotifyMortgage(val enabled : Boolean , val item : RealEstateUI) : RealAction
}