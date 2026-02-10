package com.vs.oneportfolio.main.presentaion.metals

import com.vs.oneportfolio.core.database.metals.MetalEntity
import com.vs.oneportfolio.main.presentaion.model.MetalUI

sealed interface MetalAction {
  data object  onAdding : MetalAction
    data object  onDismiss : MetalAction
  data class  onSaved(val metal : MetalEntity) : MetalAction
  data class  onEdit(val metal : MetalUI) : MetalAction

  data object OnSold : MetalAction
  data object OnDelete : MetalAction
  data object OnCancelDelete : MetalAction
  data object OnDeleteConfirm : MetalAction
}


