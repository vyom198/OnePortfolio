package com.vs.oneportfolio.main.presentaion.crypto.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vs.oneportfolio.core.database.crypto.history.SoldCrypto
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.label
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.small
import com.vs.oneportfolio.core.theme.ui.topBarTitle
import com.vs.oneportfolio.main.mapper.toCommaString
import org.koin.androidx.compose.koinViewModel

@Composable
fun SoldCryptoRoot(
    viewModel: SoldCryptoViewModel = koinViewModel(),
    onBackClick : () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SoldCryptoScreen(
        state = state,
        onBackClick = onBackClick

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoldCryptoScreen(
    state: SoldCryptoState,
    onBackClick : () -> Unit

) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(
                horizontal = 16.dp
            ),

        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onBackClick()
                            },
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text("Your Sold Crypto's",
                        style = MaterialTheme.typography.topBarTitle)

                }
            )

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)

            ) {
                items(state.soldCrypto) { item ->
                    SoldCryptoItem(item)
                }
            }
        }
    }

}

@Composable
fun SoldCryptoItem(item: SoldCrypto) {
   Column(
       modifier = Modifier
           .fillMaxWidth()
           .width(100.dp).clip(
               shape = RoundedCornerShape(12.dp)
           )
           .background(
               color = CardSurface
           ).padding(
               horizontal = 16.dp,
               vertical = 16.dp
           ),
       verticalArrangement = Arrangement.spacedBy(10.dp)
   ) {
       Text(
           text = item.name.toUpperCase(locale = Locale.current),
           style = MaterialTheme.typography.names,
           color = MaterialTheme.colorScheme.onPrimary,
       )

       Row(
           modifier = Modifier.fillMaxWidth().wrapContentWidth(),
           verticalAlignment = Alignment.CenterVertically
       ) {
           Text(
               text = "${item.quantity} Coins",
               style = MaterialTheme.typography.small,
               color = MaterialTheme.colorScheme.onPrimary,
           )
           Spacer(modifier = Modifier.weight(1f))
           Text(
               text = "Sold At: ",
               style = MaterialTheme.typography.label,
               color = MaterialTheme.colorScheme.onSurfaceVariant,
           )
           Text(
               text = "$${item.totalCurrentValue.toCommaString()}",
               style = MaterialTheme.typography.small,
               color = MaterialTheme.colorScheme.onPrimary,
           )
       }

   }
}

