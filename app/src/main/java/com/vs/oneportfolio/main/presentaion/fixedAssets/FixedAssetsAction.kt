package com.vs.oneportfolio.main.presentaion.fixedAssets
import com.vs.oneportfolio.core.database.fixedincome.FixedIncomeEntity
import com.vs.oneportfolio.main.presentaion.metals.MetalAction
import com.vs.oneportfolio.main.presentaion.model.FixedAssetUI


sealed interface FixedAssetsAction {
  object  onAdding : FixedAssetsAction
    object onDismiss : FixedAssetsAction
  data class onSaved(val item : FixedIncomeEntity) : FixedAssetsAction
  data class onNotifyMaturity(val enabled : Boolean , val item : FixedAssetUI) : FixedAssetsAction
  data class onNotifyPayment(val enabled : Boolean ,val item : FixedAssetUI) : FixedAssetsAction


  data class OnDelete(val item : FixedAssetUI) : FixedAssetsAction
  data object OnCancelDelete :FixedAssetsAction
  data object OnDeleteConfirm : FixedAssetsAction

}