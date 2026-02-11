package com.vs.oneportfolio.main.presentaion.portfoliohealth.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vs.oneportfolio.core.gemini_firebase.AssetClassBreakdown
import kotlin.math.roundToInt

@Composable
fun AssetAllocationCard(breakdown: List<AssetClassBreakdown>) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "📊 Asset Allocation",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(Color(0xFF2196F3), CircleShape)
                )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Current",
                        style = MaterialTheme.typography.labelSmall
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color(0xFF4CAF50), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Recommended",
                        style = MaterialTheme.typography.labelSmall
                    )

            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            breakdown
                .sortedByDescending { it.currentPercentage }
                .forEach { asset ->
                    AssetAllocationRow(asset)
                    Spacer(modifier = Modifier.height(12.dp))
                }
        }
    }
}

@Composable
fun AssetAllocationRow(asset: AssetClassBreakdown) {
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = asset.assetClass,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,

            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${asset.currentPercentage.toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "→",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "${asset.recommendedPercentage.toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
            
            val diff = asset.currentPercentage - asset.recommendedPercentage
            val statusColor = when {
                asset.status == "Overweight" -> Color(0xFFF44336)
                asset.status == "Underweight" -> Color(0xFFFF9800)
                else -> Color(0xFF4CAF50)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .background(
                        color = statusColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = if (diff > 0) "-${diff.roundToInt()}%" else "+${(-diff).roundToInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = statusColor,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Progress bar comparison
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Current allocation bar
            Box(
                modifier = Modifier
                    .weight(asset.currentPercentage.toFloat())
                    .height(6.dp)
                    .background(
                        color = when {
                            asset.currentPercentage > asset.recommendedPercentage + 10 -> Color(0xFFF44336)
                            asset.currentPercentage < asset.recommendedPercentage - 10 -> Color(0xFFFF9800)
                            else -> Color(0xFF4CAF50)
                        }.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(3.dp)
                    )
            )
            
            Spacer(modifier = Modifier.width(2.dp))
            
            // Target allocation bar
            Box(
                modifier = Modifier
                    .weight(asset.recommendedPercentage.toFloat())
                    .height(6.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(3.dp)
                    )
            )
        }
    }
}