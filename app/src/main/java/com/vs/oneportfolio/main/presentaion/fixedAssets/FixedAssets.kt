package com.vs.oneportfolio.main.presentaion.fixedAssets

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vs.oneportfolio.core.theme.ui.topBarTitle
import com.vs.oneportfolio.main.presentaion.crypto.CryptoAction
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.FixedAssetItem
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets.AddfixedAsset
import com.vs.oneportfolio.main.presentaion.metals.components.bottomsheet.DeleteBottomSheet
import com.vs.oneportfolio.main.presentaion.model.ObserveAsEvents
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun FixedAssetsRoot(
    viewModel: FixedAssetsViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope ()
    val snackbarHostState = remember { SnackbarHostState() }
    ObserveAsEvents(
        viewModel.events,

    ) { event ->
        when(event){
            FixedAssetEvent.onDelete -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Deleted Successfully"
                    )
                }
            }
        }
    }
    FixedAssetsScreen(
        state = state,
        onAction = viewModel::onAction ,
        onBackClick = onBackClick,
        snackbarHostState = snackbarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FixedAssetsScreen(
    state: FixedAssetsState,
    onAction: (FixedAssetsAction) -> Unit,
    onBackClick: () -> Unit,
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
        snackbarHost = { androidx.compose.material3.SnackbarHost(snackbarHostState) },
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
                                   onAction(FixedAssetsAction.onAdding)
                            },
                        tint = MaterialTheme.colorScheme.onPrimary
                    )

                },
                title = {
                    Text("Your Fixed Assets",
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
            if(state.fixedAssets.isEmpty()){
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                    ){
                    Text("No Assets")
                }

            }else{
                LazyColumn(modifier = Modifier.fillMaxSize().animateContentSize(),
                      verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                    items(state.fixedAssets) { item ->
                       FixedAssetItem(item ,
                               onNotifyPayment = {
                                   onAction(FixedAssetsAction.onNotifyPayment(it , item))
                               },
                               onNotifyMaturity = {
                                  onAction(FixedAssetsAction.onNotifyMaturity(it, item ))
                               },
                               onDelete = {
                                   onAction(FixedAssetsAction.OnDelete(item))
                               }

                           )
                    }
                }
            }

             if(
                 state.isAdding

             ){
                 AddfixedAsset(
                     onDismiss = {
                         onAction(FixedAssetsAction.onDismiss)
                     },
                     onSaved = {
                         onAction(FixedAssetsAction.onSaved(it))
                     }
                 )
             }
              
            if(state.isDeleting){
                DeleteBottomSheet(
                    onDismiss = {
                        onAction(FixedAssetsAction.OnCancelDelete)
                    },
                    OnDelete = {
                        onAction(FixedAssetsAction.OnDeleteConfirm)
                    }
                )
            }


        }
    }
}



//@Preview
//@Composable
//private fun Preview() {
//    OnePortfolioTheme {
//        FixedAssetsScreen(
//            state = FixedAssetsState(),
//            onAction = {}
//        )
//    }
//}