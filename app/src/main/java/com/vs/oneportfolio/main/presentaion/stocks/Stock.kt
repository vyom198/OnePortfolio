package com.vs.oneportfolio.main.presentaion.stocks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vs.oneportfolio.core.theme.ui.topBarTitle
import com.vs.oneportfolio.main.presentaion.metals.components.bottomsheet.DeleteBottomSheet
import com.vs.oneportfolio.main.presentaion.model.ObserveAsEvents
import com.vs.oneportfolio.main.presentaion.stocks.components.EditDropDow
import com.vs.oneportfolio.main.presentaion.stocks.components.bottomsheets.AddStockBottomSheet
import com.vs.oneportfolio.main.presentaion.stocks.components.StockItem
import com.vs.oneportfolio.main.presentaion.stocks.components.bottomsheets.UpdateStockItem
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun StockRoot(
    viewModel: StockViewModel = koinViewModel(),
    onBackClick: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    ObserveAsEvents(viewModel.events) { event ->
        when(event){
            StockEven.onSold ->{
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Stock Moved to History"
                    )
                }
            }
        }
    }
    StockScreen(
        state = state,
        onAction = viewModel::onAction,
        onBackClick = onBackClick,
        snackbarHostState = snackbarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen(
    state: StockState,
    onAction: (StockAction) -> Unit,
    onBackClick : () -> Unit,
    snackbarHostState: SnackbarHostState
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
         snackbarHost = {
             SnackbarHost(hostState = snackbarHostState)
         },
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
                actions = {

                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onAction(StockAction.onAddIconclick)
                            },
                        tint = MaterialTheme.colorScheme.onPrimary
                    )

                },
                title = {
                    Text("Your Stocks & ETFs",
                        style = MaterialTheme.typography.topBarTitle)

                }
            )

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) ,
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)

            ) {
                items(state.stocksList){ item->
                    StockItem(item,
                        onClickMenu = {
                            onAction(StockAction.OnMenuClick(it ))
                        },
                        onEdit = {
                            onAction(StockAction.onEditShareClick)
                        },
                        onDelete = {
                            onAction(StockAction.onDelete)
                        },
                        onSold = {
                            onAction(StockAction.onSoldClick)
                        },
                    )

                }
            }


            if(
                state.isDeleting
            ) {
                DeleteBottomSheet(
                   onDismiss = {
                       onAction(StockAction.onDeleteCancel)
                   },
                  OnDelete = {
                      onAction(StockAction.onDeleteConfirm)
                  }
                )
            }

            if(state.isAdding){
                AddStockBottomSheet(
                    state = state,
                    onSelect = {
                        onAction(StockAction.onSelect(it))
                    },
                    onTextChange = {
                        onAction(StockAction.onTextChange(it))
                    },
                    onClear = {
                        onAction(StockAction.Clear)
                    },
                    onDismiss = {
                        onAction(StockAction.onDismiss)
                    },
                    onAdd = {
                        onAction(StockAction.onButtonClick)
                    }
                )
            }

            if(state.addingShare){
                UpdateStockItem(
                    stock = state.currentUpdatingStock!!,
                    onDismiss = {
                        onAction(StockAction.onDismissUpdate)
                    },
                    onAdd = { quantity, amt ->
                        onAction(StockAction.onUpdateClick(
                            quantity ,
                            amt
                        ))
                    },
                    )
            }

        }

    }


}




//@Preview
//@Composable
//private fun Preview() {
//    OnePortfolioTheme {
//        StockScreen(
//            state = StockState(),
//            onAction = {}
//        )
//    }
//}