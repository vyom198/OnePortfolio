package com.vs.oneportfolio.main.presentaion.metals.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import com.vs.oneportfolio.R
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
import com.vs.oneportfolio.core.theme.ui.LossRed
import com.vs.oneportfolio.core.theme.ui.label
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.small
import com.vs.oneportfolio.main.mapper.toCommaString
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets.convertMillisToDate
import com.vs.oneportfolio.main.presentaion.metals.model.WeightUnitMapper
import com.vs.oneportfolio.main.presentaion.metals.model.convertFromOz
import com.vs.oneportfolio.main.presentaion.model.MetalUI
import com.vs.oneportfolio.main.presentaion.stocks.components.InvestedAmt

@Composable
fun MetalItem(item: MetalUI ,
              OnEdit : () -> Unit
              ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .clip(
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    color = CardSurface
                )
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = 0.8f,
                    )
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
        ) {
            var expanded by retain { mutableStateOf(false) }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.label.toUpperCase(locale = Locale.current),
                    style = MaterialTheme.typography.names,
                    color = MaterialTheme.colorScheme.onPrimary

                )
                Icon(
                    painter = painterResource(if (expanded) R.drawable.arrow_up else R.drawable.arrow_down),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            expanded = !expanded
                        },
                    contentDescription = null,
                )

            }
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                text = "${
                    convertFromOz(
                        item.weight,
                        item.unit
                    )
                } ${WeightUnitMapper.getString(item.unit)}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary

            )
            Spacer(modifier = Modifier.height(13.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InvestedAmt("Purchased At", item.purchasePrice.toCommaString())
                InvestedAmt("Current Value", item.currentPrice.toCommaString())

            }
            Spacer(modifier = Modifier.height(13.dp))
            if (expanded) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${item.karat} Karat",
                    style = MaterialTheme.typography.small,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = convertMillisToDate(item.purchaseDate),
                        style = MaterialTheme.typography.small,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }

                if (!item.storageLocation.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Storage Location: ",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = item.storageLocation,
                            style = MaterialTheme.typography.small,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(), thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Gain/Loss: ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$${item.abs.toCommaString()}",
                    style = MaterialTheme.typography.label,
                    color = if (item.isPositive) EmeraldGreen else LossRed
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Edit Details",
                    modifier = Modifier.clickable {
                        OnEdit()
                    },
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }


        }


}