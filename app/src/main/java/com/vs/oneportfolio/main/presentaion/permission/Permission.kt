package com.vs.oneportfolio.main.presentaion.permission

import android.Manifest
import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vs.oneportfolio.R
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.topBarTitle
import com.vs.oneportfolio.main.presentaion.model.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun PermissionRoot(
    viewModel: PermissionViewModel = koinViewModel(),
    NavigateToHome : () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    // ✅ Alternative - Using LaunchedEffect with Lifecycle
    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
          viewModel.onAction( PermissionAction.ResetAfterSettings)
        }
    }
    ObserveAsEvents(
        viewModel.events,
    ) { event ->
        when(event){
            PermissionEvent.Granted -> {
                NavigateToHome()
            }
        }
    }
    PermissionScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionScreen(
    state: PermissionState,
    onAction: (PermissionAction) -> Unit,
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val show = activity?.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onAction(PermissionAction.UpdatePermissionStatus(isGranted))
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = Color.Unspecified
                    )
                },
                title = {
                    Text("One Portfolio",
                        style = MaterialTheme.typography.topBarTitle,
                        color = MaterialTheme.colorScheme.onPrimary

                    )
                },
                actions = {

                }

            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.padding(32.dp)
                ) {
                    // Icon
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = if(state.count == 2){
                            "Notifications Blocked"
                        }else{

                            "Enable Notifications"
                        },

                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )

                    // Description
                    Text(
                        text = "One Portfolio needs notification permission to send you reminders and important alerts about your portfolio.",
                        style = MaterialTheme.typography.bodyLarge,
                        color =MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    // Button - Different states
                      if(state.count < 2 ){
                          Button(
                              onClick = {
                                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                      permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                  }
                              },
                              modifier = Modifier

                          ) {
                              Text(
                                  text = "Allow",
                                  style = MaterialTheme.typography.names
                              )
                          }
                        }
                        else {
                          Column(
                              horizontalAlignment = Alignment.CenterHorizontally,
                              modifier = Modifier.fillMaxWidth()
                          ) {
                              Button(
                                  onClick = { onAction(PermissionAction.OpenAppSettings)
                                  },
                                  modifier = Modifier

                              ) {
                                  Text(
                                      text = "Open Settings",
                                      style = MaterialTheme.typography.names
                                  )
                              }

                          }


                        }
                    }
                }
        }

    show?.let {
        if (it && state.shouldShowRationale) {
            PermissionRationaleDialog(
                onDismiss = {
                    onAction(PermissionAction.DismissDialog)
                },
                onConfirm = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            )
        }
    }



    }




@Composable
fun PermissionRationaleDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = {
            Text(text = "Permission Required")
        },
        text = {
            Text(
                text = "One Portfolio needs notification permission to send you:\n\n" +
                        "• Assets growth alerts  \n" +
                        "• Amortization and payment reminders",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Allow")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Not Now")
            }
        }
    )
}

