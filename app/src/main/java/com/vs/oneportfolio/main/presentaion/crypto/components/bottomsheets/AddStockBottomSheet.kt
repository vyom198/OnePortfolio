package com.vs.oneportfolio.main.presentaion.crypto.components.bottomsheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vs.oneportfolio.R
import com.vs.oneportfolio.core.finnhubNetwork.StockTicker
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
import com.vs.oneportfolio.core.theme.ui.SkyBlueAccent
import com.vs.oneportfolio.core.theme.ui.label
import com.vs.oneportfolio.core.theme.ui.normal
import com.vs.oneportfolio.main.presentaion.crypto.CryptoState
import com.vs.oneportfolio.main.presentaion.stocks.StockState
import com.vs.oneportfolio.main.presentaion.stocks.components.TickerItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStockBottomSheet(modifier: Modifier = Modifier,
                        state: CryptoState,
                        onSelect : (StockTicker) -> Unit ,
                        onTextChange : (String) -> Unit ,
                        onClear : () -> Unit ,
                        onDismiss : () -> Unit,
                        onAdd : () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = rememberModalBottomSheetState(),
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(
                horizontal = 16.dp

            ),

            ) {
            OutlinedTextField(
                value = state.text,
                textStyle = MaterialTheme.typography.normal,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = CardSurface,
                    unfocusedContainerColor = CardSurface,
                ),

                onValueChange = {
                    onTextChange(it)
                },
                modifier = Modifier.height(60.dp).fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                maxLines = 1,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = SkyBlueAccent,
                        modifier = Modifier.size(24.dp).clickable {
                           onClear()
                        }
                    )
                },
                placeholder = {
                    Text(
                        text = "search your crypto",
                        style = MaterialTheme.typography.normal,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                            alpha = 0.4f
                        )
                    )
                }
            )
            if (state.tickerList.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Crypto's" , style = MaterialTheme.typography.label,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f),
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
                        containerColor = EmeraldGreen,

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