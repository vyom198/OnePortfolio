package com.vs.oneportfolio.main.presentaion.metals

import com.vs.oneportfolio.core.database.metals.MetalEntity

sealed interface MetalAction {
  data object  onAdding : MetalAction
    data object  onDismiss : MetalAction
  data class  onSaved(val metal : MetalEntity) : MetalAction
}