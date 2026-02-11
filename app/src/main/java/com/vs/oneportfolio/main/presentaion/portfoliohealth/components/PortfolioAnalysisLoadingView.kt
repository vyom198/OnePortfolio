package com.vs.oneportfolio.main.presentaion.portfoliohealth.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun PortfolioAnalysisLoadingView(modifier: Modifier = Modifier) {
    val loadingState = remember { mutableStateOf(0) }
    val loadingText = remember { mutableStateOf("Analyzing your portfolio...") }
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing)
        )
    )
    LaunchedEffect(Unit) {
        // Rotate through loading messages
        val messages = listOf(
            "Calculating portfolio value..." to 0,
            "Analyzing asset allocation..." to 1,
            "Checking risk concentration..." to 2,
            "Generating recommendations..." to 3,
            "Almost there..." to 4
        )
        
        messages.forEachIndexed { index, (text, state) ->
            delay(if (index == 0) 0 else 1200)
            loadingText.value = text
            loadingState.value = state
        }
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .size(96.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .graphicsLayer {
                        rotationZ = rotation },
                tint = MaterialTheme.colorScheme.primary
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Main loading message
        Text(
            text = loadingText.value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Progress Stepper
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LoadingStep(
                step = 0,
                currentStep = loadingState.value,
                label = "Value"
            )
            LoadingStep(
                step = 1,
                currentStep = loadingState.value,
                label = "Allocation"
            )
            LoadingStep(
                step = 2,
                currentStep = loadingState.value,
                label = "Risk"
            )
            LoadingStep(
                step = 3,
                currentStep = loadingState.value,
                label = "Recommend"
            )
            LoadingStep(
                step = 4,
                currentStep = loadingState.value,
                label = "Done"
            )
        }
        
       Spacer(modifier = Modifier.height(30.dp))
        
        // Tip of the day
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "💡 Did you know?",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                
                val tips = listOf(
                    "Diversification can reduce risk without sacrificing returns.",
                    "Rebalancing annually helps maintain your target allocation.",
                    "Fixed income provides stability during market downturns.",
                    "Young investors can afford more risk due to longer time horizons.",
                    "Single stocks have 3x more volatility than diversified ETFs."
                )
                
                Text(
                    text = tips[loadingState.value.coerceAtMost(4)],
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))

    }
}

@Composable
fun LoadingStep(
    step: Int,
    currentStep: Int,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = when {
                        step < currentStep -> Color(0xFF4CAF50) // Done
                        step == currentStep -> MaterialTheme.colorScheme.primary // Current
                        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f) // Pending
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            when {
                step < currentStep -> {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
                step == currentStep -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                }
                else -> {
                    Text(
                        text = (step + 1).toString(),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = when {
                step <= currentStep -> MaterialTheme.colorScheme.onSurface
                else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            }
        )
    }
}
