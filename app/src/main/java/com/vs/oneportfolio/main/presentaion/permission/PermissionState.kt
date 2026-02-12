package com.vs.oneportfolio.main.presentaion.permission

data class PermissionState(
    val shouldShowRationale: Boolean = false,
    val hasPermission :Boolean = false,
    val count : Int = 0
)
