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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vs.oneportfolio.core.gemini_firebase.ComprehensivePortfolioAnalysis

@Composable
fun CriticalIssuesCard(analysis: ComprehensivePortfolioAnalysis) {
    // Find the most critical issues
    val crypto = analysis.diversificationAnalysis.assetClassBreakdown
        .find { it.assetClass.contains("Crypto", ignoreCase = true) }
    
    val stocks = analysis.diversificationAnalysis.assetClassBreakdown
        .find { it.assetClass.contains("Stocks", ignoreCase = true) }
    
    val fixedIncome = analysis.diversificationAnalysis.assetClassBreakdown
        .find { it.assetClass.contains("Fixed", ignoreCase = true) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF3E0)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color(0xFFF44336),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Critical Issues",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF44336)
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            if (crypto != null && crypto.currentPercentage > 20) {
                CriticalIssueItem(
                    issue = "${crypto.assetClass}: ${crypto.currentPercentage.toInt()}%",
                    target = "Target: ${crypto.recommendedPercentage.toInt()}%",
                    severity = "CRITICAL"
                )
            }
            
            if (stocks != null && stocks.currentPercentage > 0) {
                CriticalIssueItem(
                    issue = "Single Stock Risk: Apple (${stocks.currentPercentage.toInt()}%)",
                    target = "Diversify to ETFs",
                    severity = "HIGH"
                )
            }
            
            if (fixedIncome != null && fixedIncome.currentPercentage < 5) {
                CriticalIssueItem(
                    issue = "Fixed Income: ${fixedIncome.currentPercentage.toInt()}%",
                    target = "Target: ${fixedIncome.recommendedPercentage.toInt()}%",
                    severity = "HIGH"
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = analysis.diversificationAnalysis.sectorConcentration.concentrationRisk
                    .take(100) + "...",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun CriticalIssueItem(
    issue: String,
    target: String,
    severity: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = issue,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = target,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Box(
            modifier = Modifier
                .background(
                    color = if (severity == "CRITICAL") Color(0xFFF44336) 
                            else Color(0xFFFF9800),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = severity,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}