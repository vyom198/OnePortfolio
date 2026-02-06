package com.vs.oneportfolio.main.presentaion.fixedAssets.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vs.oneportfolio.core.theme.ui.DeepNavyBg
import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
import com.vs.oneportfolio.core.theme.ui.LossRed

@Composable
fun CustomFDProgress(
    openedDate: Long,
    maturityDate: Long,
    modifier: Modifier = Modifier,
    onProgressChange: (Float) -> Unit
) {
    // 1. Calculate the percentage (0.0 to 1.0)
    val progress = remember(openedDate, maturityDate) {
        val total = maturityDate - openedDate
        val elapsed = System.currentTimeMillis() - openedDate
        if (total > 0) (elapsed.toFloat() / total).coerceIn(0f, 1f) else 0f
    }

    LaunchedEffect(
        key1 = progress
    ) {
        onProgressChange(progress)
    }
    Canvas(modifier = modifier
        .fillMaxWidth()
        .height(10.dp) // Slightly thicker for a premium feel
    ) {
        val width = size.width
        val height = size.height
        val cornerRadius = CornerRadius(height / 2, height / 2)
        val targetColor = when {
            progress >= 0.9f -> LossRed // Success Green
            progress >= 0.6f -> Color(0xFFFF9800) // Warning Orange
            else -> EmeraldGreen             // Primary Blue
        }

        drawRoundRect(
            color = DeepNavyBg,
            size = size,
            cornerRadius = cornerRadius
        )

        // 3. Draw Foreground Progress (The "Filled" part)
        drawRoundRect(
            color = targetColor,
            size = size.copy(width = width * progress),
            cornerRadius = cornerRadius
        )

    }
}