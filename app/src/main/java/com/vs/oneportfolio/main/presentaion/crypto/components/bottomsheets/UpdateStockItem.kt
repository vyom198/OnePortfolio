package com.vs.oneportfolio.main.presentaion.crypto.components.bottomsheets
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
import com.vs.oneportfolio.core.theme.ui.SkyBlueAccent
import com.vs.oneportfolio.core.theme.ui.Values
import com.vs.oneportfolio.core.theme.ui.label
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.normal
import com.vs.oneportfolio.core.theme.ui.small
import com.vs.oneportfolio.main.mapper.formats
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets.AddAssetTextField
import com.vs.oneportfolio.main.presentaion.model.CryptoUI
import com.vs.oneportfolio.main.presentaion.model.StockUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateStockItem(modifier: Modifier = Modifier,
                        stock : CryptoUI,
                        onDismiss : () -> Unit,
                        onAdd : (Double , Double ) -> Unit
) {
    var totalCoins by remember { mutableStateOf("") }
    var totalAmtInvested by remember { mutableStateOf("") }
    var totalCoinsSold by remember { mutableStateOf("") }
    var soldAmount by remember { mutableStateOf("") }
    val quantity = if(totalCoins.isNotEmpty() ){
        totalCoins.toDouble()
    }else{
        if(totalCoinsSold.isNotEmpty()){
            -totalCoinsSold.toDouble()
        } else 0
    }
    val amt =
        if(totalAmtInvested.isNotEmpty()){
            totalAmtInvested.toDouble()
        }else{
            if(soldAmount.isNotEmpty()){
                -soldAmount.toDouble()
            } else 0.0
        }
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = null ,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        modifier = Modifier.imePadding()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(
                horizontal = 16.dp,
                vertical = 16.dp

            ),

            ) {
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight() ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Edit ${stock.name.split(" ")[0]} Holdings" , style = MaterialTheme.typography.names,
                    color = MaterialTheme.colorScheme.onSurface)
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(32.dp).clickable{
                        onDismiss()
                    }
                )

            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Add more coins" , style = MaterialTheme.typography.label,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(16.dp))
            AddAssetTextField(
                onTextChange = {
                    totalAmtInvested = it
                },
                onClear = {
                    totalAmtInvested = ""
                },
                isNumber = true ,
                text = totalAmtInvested,
                label = "Amount Invested",
                hint = "e.g. $4000"

            )
            Spacer(modifier = Modifier.height(16.dp))
            AddAssetTextField(
                onTextChange = {
                    totalCoins = it
                },
                onClear = {
                    totalCoins = ""
                },
                isNumber = true ,
                text = totalCoins,
                label = "Total Coins Recieved",
                hint = "e.g. 3.5 "

            )

            if(totalCoins.isNotEmpty() && totalAmtInvested.isNotEmpty()){
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "This averages out to $${(totalAmtInvested.toDouble()/totalCoins.toDouble()).formats()} per coin.",
                    style = MaterialTheme.typography.small,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Or" , style = MaterialTheme.typography.normal.copy(
                fontWeight = FontWeight.W600
            ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Log your sold coins" , style = MaterialTheme.typography.label,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(16.dp))
            AddAssetTextField(
                onTextChange = {
                    soldAmount = it
                },
                onClear = {
                    soldAmount = ""
                },
                isNumber = true ,
                text = soldAmount,
                label = "Coins sold At",
                hint = "e.g. $3000"

            )
            Spacer(modifier = Modifier.height(16.dp))
            AddAssetTextField(
                onTextChange = {
                    totalCoinsSold = it
                },
                onClear = {
                    totalCoinsSold = ""
                },
                isNumber = true ,
                text = totalCoinsSold,
                label = "Coins Sold ",
                hint = "e.g. 3.5 "

            )
            if(totalCoinsSold.isNotEmpty() && soldAmount.isNotEmpty()){
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "This averages out to $${(soldAmount.toDouble()/totalCoinsSold.toDouble()).formats()} per coin.",
                    style = MaterialTheme.typography.small,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {onAdd(
                    quantity.toDouble(), amt
                )},
                enabled = totalCoins.isNotEmpty() && totalAmtInvested.isNotEmpty()
                        || totalCoinsSold.isNotEmpty() && soldAmount.isNotEmpty(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SkyBlueAccent,

                    ),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text(
                    text = "Confirm",
                    style = MaterialTheme.typography.normal
                )

            }


        }
    }
}