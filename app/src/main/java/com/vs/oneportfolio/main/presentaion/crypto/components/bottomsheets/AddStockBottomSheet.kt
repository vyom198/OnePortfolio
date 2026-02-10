package com.vs.oneportfolio.main.presentaion.crypto.components.bottomsheets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vs.oneportfolio.core.finnhubNetwork.cryptoDtos.CoinMetadata
import com.vs.oneportfolio.core.finnhubNetwork.stockDtos.StockTicker
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
import com.vs.oneportfolio.core.theme.ui.SkyBlueAccent
import com.vs.oneportfolio.core.theme.ui.label
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.normal
import com.vs.oneportfolio.core.theme.ui.small
import com.vs.oneportfolio.main.presentaion.crypto.CryptoState
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets.AddAssetTextField
import com.vs.oneportfolio.main.presentaion.stocks.components.TickerItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStockBottomSheet(modifier: Modifier = Modifier,
                        state: CryptoState,
                        onSelect : (CoinMetadata) -> Unit,
                        onTextChange : (String) -> Unit,
                        onClear : () -> Unit,
                        onDismiss : () -> Unit,
                        onAdd : () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        dragHandle = null,
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        modifier = Modifier.imePadding()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),

            ) {
            Text(
                text = "Search",
                style = MaterialTheme.typography.small,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(10.dp))
            AddAssetTextField(
                onTextChange = onTextChange,
                onClear = onClear,
                text = state.text,
                label = " Search your Crypto",
                hint = "e.g. Bitcoin "
            )
            if(state.tickerList.isEmpty()){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if(state.text.isEmpty()) "Empty Searches" else "No Results Found",
                        style = MaterialTheme.typography.small,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }

            }
            if (state.tickerList.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Crypto's" , style = MaterialTheme.typography.label,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)

                ) {

                    items(
                        state.tickerList
                    ) {

                        CoinItem(
                            it,
                            isSelected = it == state.selectedTicker,
                            onSelected = { ticker ->
                                onSelect(ticker)

                            })
                    }
                }
            }
            if(state.selectedTicker !=null){
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onAdd,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SkyBlueAccent,

                        ),
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text(
                        text = "Add to Assets",
                        style = MaterialTheme.typography.normal
                    )

                }
            }

        }
    }
}

@Composable
fun CoinItem(item: CoinMetadata , isSelected : Boolean , onSelected : (CoinMetadata) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().clip(
            shape = RoundedCornerShape(16.dp),
        ).background(
            color = CardSurface
        ).border(
            width = if(isSelected)2.dp else 0.dp ,
            color = if(isSelected) SkyBlueAccent else CardSurface,
            shape = RoundedCornerShape(16.dp)
        ).clickable{
            onSelected(item)
        }.padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        AsyncImage(
            model = item.logo,
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.slug,
                style = MaterialTheme.typography.names,
                color = MaterialTheme.colorScheme.onPrimary

            )
            Text(
                text = item.symbol,
                style = MaterialTheme.typography.names,
                color = MaterialTheme.colorScheme.onSurfaceVariant

            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if(isSelected){
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint =SkyBlueAccent ,
                modifier = Modifier.size(24.dp)

            )
        }


    }
}