package com.vs.oneportfolio.main.presentaion.fixedAssets

import com.vs.oneportfolio.main.presentaion.model.FixedAssetUI

data class FixedAssetsState(
   val isAdding : Boolean = false,
   val fixedAssets : List<FixedAssetUI> = emptyList()
)


