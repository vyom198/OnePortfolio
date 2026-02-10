package com.vs.oneportfolio.main.presentaion.fixedAssets.history

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vs.oneportfolio.core.database.fixedincome.history.MaturedFEntity
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.label
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.small
import com.vs.oneportfolio.core.theme.ui.topBarTitle
import com.vs.oneportfolio.main.mapper.toCommaString
import com.vs.oneportfolio.main.presentaion.fixedAssets.FixedAssetsAction
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.FixedAssetItem
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets.convertMillisToDate
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FixedHistoryRoot(
    viewModel: FixedHistoryViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FixedHistoryScreen(
        state = state,
        onAction = viewModel::onAction,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedHistoryScreen(
    state: FixedHistoryState,
    onAction: (FixedHistoryAction) -> Unit,
    onBackClick: () -> Unit,
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
                    Text("Your Matured Assets",
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
            if(state.list.isEmpty()){
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ){
                    Text("No Matured Assets")
                }

            }else{
                LazyColumn(modifier = Modifier.fillMaxSize().animateContentSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.list) { item ->
                       MaturedAssetItem(item)
                    }
                }
            }
        }
    }
}

@Composable
fun MaturedAssetItem(item : MaturedFEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .width(120.dp).clip(
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = CardSurface
            ).padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),

    ) {
        Text(
            text = item.depositName.toUpperCase(locale = Locale.current),
            style = MaterialTheme.typography.names,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Principal Amount: ",
                style = MaterialTheme.typography.small,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "$${item.amtPrincipal.toCommaString()}",
                style = MaterialTheme.typography.small,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "At Maturity: ",
                style = MaterialTheme.typography.small,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "$${item.currentValue.toCommaString()}",
                style = MaterialTheme.typography.small,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = convertMillisToDate(item.dateMaturity),
                style = MaterialTheme.typography.small,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

    }
}

