package com.vs.oneportfolio.main.presentaion.permission

sealed interface PermissionAction {
    data object DismissDialog : PermissionAction
    data object OpenAppSettings : PermissionAction
    data class UpdatePermissionStatus(val isGranted: Boolean) : PermissionAction
    data object ResetAfterSettings : PermissionAction
}