package com.vs.oneportfolio.main.presentaion.fixedAssets

sealed interface FixedAssetsAction {
  object  onAdding : FixedAssetsAction
    object onDismiss : FixedAssetsAction
}