package com.vs.oneportfolio.main.presentaion.metals

import com.vs.oneportfolio.main.presentaion.model.MetalUI

data class MetalState(
  val metalList : List<MetalUI> = emptyList(),
  val isAdding : Boolean = false ,
  val currentMetal : MetalUI? = null,
  val isDeleting : Boolean = false
)