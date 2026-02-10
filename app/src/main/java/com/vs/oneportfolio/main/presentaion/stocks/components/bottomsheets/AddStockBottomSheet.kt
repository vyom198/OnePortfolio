package com.vs.oneportfolio.main.presentaion.stocks.components.bottomsheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vs.oneportfolio.R
import com.vs.oneportfolio.core.finnhubNetwork.cryptoDtos.CoinMetadata
import com.vs.oneportfolio.core.finnhubNetwork.stockDtos.StockTicker
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
import com.vs.oneportfolio.core.theme.ui.SkyBlueAccent
import com.vs.oneportfolio.core.theme.ui.label
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.normal
import com.vs.oneportfolio.core.theme.ui.small
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets.AddAssetTextField
import com.vs.oneportfolio.main.presentaion.stocks.StockState
import com.vs.oneportfolio.main.presentaion.stocks.components.TickerItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStockBottomSheet(modifier: Modifier = Modifier,
                        state: StockState,
                        onSelect : (StockTicker) -> Unit,
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
                horizontal = 16.dp ,
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
                label = " Search your Stock",
                hint = "e.g. AAPL or Apple "
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
                Text(text = "Stocks" , style = MaterialTheme.typography.label,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)

                ) {

                    items(
                        state.tickerList
                    ) {

                        TickerItem(
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