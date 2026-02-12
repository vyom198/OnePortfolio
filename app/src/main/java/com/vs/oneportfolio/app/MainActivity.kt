package com.vs.oneportfolio.app

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.vs.oneportfolio.app.navigation.NavigationRoot
import com.vs.oneportfolio.core.theme.ui.OnePortfolioTheme
import org.koin.android.ext.android.inject
import kotlin.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPrefs: SharedPreferences by inject()

        val isPermissionAlreadyGranted = sharedPrefs.getBoolean("permission_granted", false)
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
        )

        setContent {
            OnePortfolioTheme {
                NavigationRoot(
                    navController = rememberNavController(),
                    isPermissionAlreadyGranted =isPermissionAlreadyGranted
                )
            }
        }
    }
}