package com.vs.oneportfolio.main.presentaion.realestate

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vs.oneportfolio.core.theme.ui.topBarTitle
import com.vs.oneportfolio.main.presentaion.fixedAssets.FixedAssetsAction
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.FixedAssetItem
import com.vs.oneportfolio.main.presentaion.model.RealEstateUI
import com.vs.oneportfolio.main.presentaion.realestate.component.RealEstateItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun RealRoot(
    viewModel: RealViewModel = koinViewModel(),
    onBack: () -> Unit,
    onAddClick : (String) -> Unit ,
    onEditClick : (String, Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RealScreen(
        state = state,
        onAction = viewModel::onAction,
        onBack = onBack,
        onAddClick = onAddClick,
        onEditClick = onEditClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealScreen(
    state: RealState,
    onAction: (RealAction) -> Unit,
    onBack: () -> Unit,
    onAddClick : (String) -> Unit ,
    onEditClick : (String, Int) -> Unit
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
                                onBack()
                            },
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {

                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onAddClick("Add Your Real Estate")
                            },
                        tint = MaterialTheme.colorScheme.onPrimary
                    )

                },
                title = {
                    Text("Your Real Estates",
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
            if (state.realestates.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text("No Assets")
                }

            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().animateContentSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.realestates) { item ->
                        RealEstateItem(item,
                            onNotifyRent = {
                                onAction(RealAction.onNotifyRental(it , item))
                            },
                            onNotifyMortgage = {
                                onAction(RealAction.onNotifyMortgage(it , item))
                            },
                            onNotifyTax = {
                                onAction(RealAction.onNotifyTax(it , item))

                            },
                            onEditClick = {
                                onEditClick(item.propertyName, it)
                            }
                        )
                    }
                }
            }
        }
    }
}



//@Preview
//@Composable
//private fun Preview() {
//    OnePortfolioTheme {
//        RealScreen(
//            state = RealState(),
//            onAction = {}
//        )
//    }
//}