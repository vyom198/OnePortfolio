package com.vs.oneportfolio.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.vs.oneportfolio.app.navigation.NavigationRoot
import com.vs.oneportfolio.core.theme.ui.OnePortfolioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnePortfolioTheme {
                NavigationRoot(
                    navController = rememberNavController()
                )
            }
        }
    }
}