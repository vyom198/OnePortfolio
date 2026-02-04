package com.vs.oneportfolio.main.presentaion.stocks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vs.oneportfolio.core.finnhubNetwork.StockTicker
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
import com.vs.oneportfolio.core.theme.ui.names

@Composable
fun TickerItem(item: StockTicker , isSelected : Boolean , onSelected : (StockTicker) -> Unit) {
    Row(
      modifier = Modifier.fillMaxWidth().wrapContentHeight().clip(
          shape = RoundedCornerShape(16.dp),
      ).background(
          color = CardSurface
      ).border(
          width = if(isSelected)2.dp else 0.dp ,
          color = if(isSelected) EmeraldGreen else CardSurface,
          shape = RoundedCornerShape(16.dp)
      ).clickable{
          onSelected(item)
      }.padding(all = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = item.description,
                style = MaterialTheme.typography.names,
                color = MaterialTheme.colorScheme.onPrimary

            )
            Text(
                text = item.symbol,
                style = MaterialTheme.typography.names,
                color = MaterialTheme.colorScheme.onSurfaceVariant

            )
        }
        if(isSelected){
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = EmeraldGreen ,
                modifier = Modifier.size(24.dp)

            )
        }


    }
}