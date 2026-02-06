package com.vs.oneportfolio.main.presentaion.fixedAssets.components

import android.graphics.Color
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.vs.oneportfolio.R
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.DeepNavyBg
import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
import com.vs.oneportfolio.core.theme.ui.SkyBlueAccent
import com.vs.oneportfolio.core.theme.ui.Values
import com.vs.oneportfolio.core.theme.ui.label
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.small
import com.vs.oneportfolio.main.mapper.formats
import com.vs.oneportfolio.main.mapper.toCommaString
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets.convertMillisToDate
import com.vs.oneportfolio.main.presentaion.model.FixedAssetUI
import com.vs.oneportfolio.main.presentaion.stocks.components.InvestedAmt
import java.util.concurrent.TimeUnit


@Composable
fun FixedAssetItem(item: FixedAssetUI) {
    Column(
      modifier = Modifier.fillMaxWidth().wrapContentSize().clip(
          shape = RoundedCornerShape(12.dp)
      ).background(
          color = CardSurface
      ).animateContentSize(
          animationSpec = spring(
              dampingRatio = 0.8f,
          )
      ).padding(
          horizontal = 16.dp,
          vertical = 16.dp
      )
    ){
        var expanded by remember{ mutableStateOf(false) }
        var progress by remember { mutableStateOf(0f) }
        Row(
         modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = item.depositName.toUpperCase(locale = Locale.current),
                style = MaterialTheme.typography.names,
                color = MaterialTheme.colorScheme.onPrimary

            )
            Icon(
                painter = painterResource(if(expanded) R.drawable.arrow_up else R.drawable.arrow_down),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(32.dp).clickable{
                    expanded = !expanded
                },
                contentDescription = null,
            )

        }
        Spacer(modifier = Modifier.height(13.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            InvestedAmt("Principal Amount" , item.amtPrincipal.toCommaString())
            InvestedAmt("Current Worth" , item.currentValue.toCommaString())

        }
        Spacer(modifier = Modifier.height(13.dp))
        Text(text = "${item.interestRatePercent}% Yield Per Year" ,
            style = MaterialTheme.typography.label,
            color = EmeraldGreen
            )

        if(expanded){
            Spacer(modifier = Modifier.height(10.dp))
            BoxWithConstraints (
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                contentAlignment = Alignment.Center
            ){
                maxWidth
                CustomFDProgress(
                    openedDate = item.dateOpened,
                    maturityDate = item.dateMaturity,
                    onProgressChange = {
                        progress = it
                    }
                )

            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically){

                Text(
                    text = convertMillisToDate(item.dateOpened) ,
                    style = MaterialTheme.typography.small,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = convertMillisToDate(item.dateMaturity) ,
                    style = MaterialTheme.typography.small,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically){
                Icon(
                    imageVector = Icons.Outlined.WatchLater,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Days Left: ${TimeUnit.MILLISECONDS.
                    toDays(item.dateMaturity - System.currentTimeMillis())}" ,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if(item.isCumulative){ "Compounding" }else {"Income"} ,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }
            Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "status: ${(progress * 100f).toDouble().formats()}%" ,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Reminders" ,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight().clip(
                    shape = RoundedCornerShape(12.dp)
                ).padding(
                    16.dp
                ).background(
                    color = MaterialTheme.colorScheme.background
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var enabled by remember { mutableStateOf(false) }
                Icon(
                    imageVector = Icons.Filled.CurrencyExchange,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = SkyBlueAccent
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)  ,
                    modifier = Modifier.fillMaxWidth(0.6f).wrapContentHeight()

                ) {
                    Text(
                        text = "Maturity Alert" ,
                        style = MaterialTheme.typography.names
                    )
                    Text(
                        text = "Notify me 7 days before Maturity" ,
                        style = MaterialTheme.typography.Values
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = enabled,
                    onCheckedChange = {
                        enabled = it
                    }
                )

             }




        }



    }
}