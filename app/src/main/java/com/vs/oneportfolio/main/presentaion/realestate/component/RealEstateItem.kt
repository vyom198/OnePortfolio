package com.vs.oneportfolio.main.presentaion.realestate.component

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vs.oneportfolio.R
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
import com.vs.oneportfolio.core.theme.ui.Values
import com.vs.oneportfolio.core.theme.ui.label
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.small
import com.vs.oneportfolio.main.mapper.toCommaString
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.ReminderItem
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets.convertMillisToDate
import com.vs.oneportfolio.main.presentaion.model.RealEstateUI
import com.vs.oneportfolio.main.presentaion.stocks.components.InvestedAmt

@Composable
fun RealEstateItem(item: RealEstateUI
                   ,onNotifyRent : (Boolean) -> Unit
                   ,onNotifyMortgage : (Boolean) -> Unit,
                   onNotifyTax : (Boolean) -> Unit,
                   onEditClick : (Int) -> Unit

) {
    val local = Locale.current
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
    ){
        var expanded by remember{ mutableStateOf(false) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            AsyncImage(
                model = item.properImg,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp).clip(
                        shape = CircleShape
                    ) ,
                error = painterResource(R.drawable.img_1),
                contentScale = ContentScale.FillBounds,
            )

            Icon(
                painter = painterResource(if(expanded) R.drawable.arrow_up else R.drawable.arrow_down),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        expanded = !expanded
                    },
                contentDescription = null,
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.propertyName.toUpperCase(locale = local),
            style = MaterialTheme.typography.names,
            color = MaterialTheme.colorScheme.onPrimary

        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            InvestedAmt("Bought At: " , item.purchasePrice.toCommaString())
            InvestedAmt("Current Worth" , item.currentMarketValue.toCommaString())

        }
        Spacer(modifier = Modifier.height(13.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${item.yieldRate}% Yield Per Year",
                style = MaterialTheme.typography.label,
                color = EmeraldGreen
            )
            Icon(
                imageVector = Icons.Default.EditNote,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .size(24.dp).clickable{
                        onEditClick(item.id)
                    },

                contentDescription = null,

            )
        }

        if(expanded){
            Spacer(modifier = Modifier.height(10.dp))
            if(!item.address.isNullOrEmpty()){
                Row(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = item.address,
                        maxLines = 2,
                        style = MaterialTheme.typography.small,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
               ){
                Text(
                    text = item.propertyType.toUpperCase(locale =local),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Purchased: ",
                    style = MaterialTheme.typography.label,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = convertMillisToDate(item.purchaseDate),
                    style = MaterialTheme.typography.small,
                    color = MaterialTheme.colorScheme.onPrimary
                )

            }
            if(item.hasMortgage){
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = "Mortgage: " ,
                        style = MaterialTheme.typography.small,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$${item.mortgageBalance?.toCommaString()}" ,
                        style = MaterialTheme.typography.small,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Mortgage Payment: ",
                        style = MaterialTheme.typography.small,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$${item.mortgagePayment?.toCommaString()}",
                        style = MaterialTheme.typography.small,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            if(item.isRented){
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rental Income: ",
                        style = MaterialTheme.typography.label,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    item.monthlyRent?.toCommaString()?.let {
                        Text(
                            text = "$${it}",
                            style = MaterialTheme.typography.small,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
            if(item.taxDueDate !== 0L){
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tax Due: ",
                        style = MaterialTheme.typography.label,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = convertMillisToDate(item.taxDueDate),
                        style = MaterialTheme.typography.small,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically){

            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Reminders" ,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if(item.isRented){
                Spacer(modifier = Modifier.height(10.dp))
                ReminderItem(
                    title = "Rent Alert",
                    desc = "Notify on rent payment",
                    icon =  Icons.Filled.CurrencyExchange ,
                    enabled = item.rentReminder,
                    onCheckedChange = {
                        onNotifyRent(it)
                    }
                )
            }
            if(item.hasMortgage){
                Spacer(modifier = Modifier.height(6.dp))
                ReminderItem(
                    title = "Mortgage Payment",
                    desc = "Notify me 7 days before",
                    icon =  Icons.Filled.Payment,
                    enabled = item.mortgageReminder,
                    onCheckedChange = {
                        onNotifyMortgage(it)
                    }
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            ReminderItem(
                title = "Tax Payment",
                desc = "Notify me 7 days before",
                icon =  Icons.Filled.AttachMoney,
                enabled = item.taxReminder,
                onCheckedChange = {
                    onNotifyTax(it)
                }
            )


        }



    }
}