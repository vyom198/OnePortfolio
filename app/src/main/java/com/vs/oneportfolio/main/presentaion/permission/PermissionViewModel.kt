package com.vs.oneportfolio.main.presentaion.permission

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class PermissionViewModel(
    private val context : Context,
    private val prefs: SharedPreferences
) : ViewModel() {

    private var hasLoadedInitialData = false

    companion object {
        private const val PERMISSION_GRANTED_KEY = "permission_granted"
    }

    private val eventChannel = Channel<PermissionEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(PermissionState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {

                checkInitialPermissionStatus()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = PermissionState()
        )

    private fun checkInitialPermissionStatus() {
        viewModelScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
                _state.update {
                    it.copy(
                        hasPermission = hasPermission

                    )
                }
                if (hasPermission) {
                    // Permission already granted
                    prefs.edit().putBoolean(PERMISSION_GRANTED_KEY, true).apply()
                    _state.update {
                        it.copy(
                            shouldShowRationale = false,
                        )
                    }
                    eventChannel.send(PermissionEvent.Granted)
                }
            } else {
                eventChannel.send(PermissionEvent.Granted)
            }
        }
    }

    fun onAction(action: PermissionAction) {
        when (action) {

            is PermissionAction.DismissDialog -> {
                _state.update {
                    it.copy(
                          shouldShowRationale = false
                    )
                }
            }

            is PermissionAction.UpdatePermissionStatus -> {
                    viewModelScope.launch {
                        if (action.isGranted) {
                            prefs.edit()
                                .putBoolean(PERMISSION_GRANTED_KEY, true)
                                .apply()

                            _state.update {
                                it.copy(
                                    shouldShowRationale = false,
                                )
                            }
                            eventChannel.send(PermissionEvent.Granted)
                        } else {

                            _state.update {
                                it.copy(
                                    shouldShowRationale = true,
                                    count = it.count + 1

                                )
                            }
                        }
                    }

            }


            is PermissionAction.OpenAppSettings -> {
                viewModelScope.launch {
                openAppSettings()
            }
            }
            is PermissionAction.ResetAfterSettings -> {
                // Check permission again after returning from settings
                checkInitialPermissionStatus()
            }
        }
    }

    private fun openAppSettings() {
        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)


    }




}