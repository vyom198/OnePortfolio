package com.vs.oneportfolio.main.presentaion.stocks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
import com.vs.oneportfolio.core.theme.ui.LossRed
import com.vs.oneportfolio.core.theme.ui.Values
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.normal
import com.vs.oneportfolio.main.mapper.toCommaString
import com.vs.oneportfolio.main.presentaion.model.StockUI


@Composable
fun StockItem(item: StockUI,
              onClickMenu : (StockUI) -> Unit,
              onEdit : () -> Unit,
              onDelete : () -> Unit,
              onSold : () -> Unit ,

){
    var enabled by remember { mutableStateOf(false) }
    Column(
      modifier = Modifier.fillMaxWidth().wrapContentHeight().clip(
          shape = RoundedCornerShape(16.dp)
      ).background(
          color = MaterialTheme.colorScheme.tertiary
      ).padding(
          vertical = 10.dp,
          horizontal = 10.dp
      )
    ){
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NameComponent(item.name,item.ticker , item.quantity)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if(item.isPositive)Icons.Filled.TrendingUp else Icons.Filled.TrendingDown,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if(item.isPositive) EmeraldGreen else LossRed

            )
            Text(
                text = if(item.isPositive)" +${item.absPercentage}%" else " -${item.absPercentage}%",
                style = MaterialTheme.typography.normal.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp,
                    color = if(item.isPositive) EmeraldGreen else LossRed

                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            InvestedAmt("Invested",item.averagePrice.toCommaString())
            Spacer(modifier = Modifier.weight(1f))
            InvestedAmt("Current Value",item.currentPrice.toCommaString())
        }
        Spacer(modifier = Modifier.height(15.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Gain/Loss: ",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant

            )
            Text(
                text = "$${item.absGainOrLoss}",
                style = MaterialTheme.typography.labelMedium,
                color = if (item.isPositive) EmeraldGreen else LossRed

            )

            Spacer(modifier = Modifier.weight(1f))
              Icon(
                  imageVector = Icons.Default.MoreVert,
                  contentDescription = null,
                  modifier = Modifier.size(24.dp).clickable{
                      onClickMenu(item)
                      enabled = true
                  },
                  tint = MaterialTheme.colorScheme.onSurfaceVariant


              )
             EditDropDow (

                 onEdit = {
                     onEdit()
                     enabled = false

                 },
                 onDelete = {
                     onDelete()
                     enabled = false
                 },
                 onSold = {
                     onSold()
                     enabled = false
                 },
                 isMenuEnabled = enabled,
                 OnDismiss = {
                     enabled = false
                 }


            )

        }
    }
}

@Composable
fun NameComponent(name: String, ticker: String , quantity : Int ) {
    Column(
        modifier = Modifier.wrapContentHeight().fillMaxWidth(0.5f) ,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.names
        )

        Text(
            text = ticker,
            style = MaterialTheme.typography.names,
                color = MaterialTheme.colorScheme.onSurfaceVariant

            )
        Text(
            text = "shares: ${quantity}",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant

        )

    }
}
@Composable
fun InvestedAmt(text: String, amt : String) {
    Column(
        modifier = Modifier.wrapContentSize() ,
        verticalArrangement = Arrangement.spacedBy(3.dp) ,

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant


        )

        Text(
            text = "$${amt}",
            style = MaterialTheme.typography.Values


        )
    }
}

