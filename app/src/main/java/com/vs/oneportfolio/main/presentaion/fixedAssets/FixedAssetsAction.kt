package com.vs.oneportfolio.main.presentaion.fixedAssets
import com.vs.oneportfolio.core.database.fixedincome.FixedIncomeEntity


sealed interface FixedAssetsAction {
  object  onAdding : FixedAssetsAction
    object onDismiss : FixedAssetsAction
  data class onSaved(val item : FixedIncomeEntity) : FixedAssetsAction
}