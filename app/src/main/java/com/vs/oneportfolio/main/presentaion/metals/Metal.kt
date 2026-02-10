package com.vs.oneportfolio.main.presentaion.metals

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.small
import com.vs.oneportfolio.core.theme.ui.topBarTitle
import com.vs.oneportfolio.main.presentaion.metals.components.MetalItem
import com.vs.oneportfolio.main.presentaion.metals.components.bottomsheet.AddMetal
import com.vs.oneportfolio.main.presentaion.metals.components.bottomsheet.DeleteBottomSheet
import com.vs.oneportfolio.main.presentaion.model.MetalUI
import com.vs.oneportfolio.main.presentaion.model.ObserveAsEvents
import com.vs.oneportfolio.main.presentaion.stocks.StockAction
import com.vs.oneportfolio.main.presentaion.stocks.components.StockItem
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MetalRoot(
    viewModel: MetalViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(
        viewModel.events,

    ) { event ->
        when(event){
            MetalEvent.SoldEvent -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Item moved to History"
                    )
                }
            }
        }
    }
    MetalScreen(
        state = state,
        onAction = viewModel::onAction,
        onBackClick = onBackClick,
        snackbarHostState = snackbarHostState


    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetalScreen(
    state: MetalState,
    onAction: (MetalAction) -> Unit,
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                                    onAction(MetalAction.onAdding)
                                },
                            tint = MaterialTheme.colorScheme.onPrimary
                        )


                },
                title = {
                    Text("Your Physical Gold",
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

            if(state.isDeleting){
                DeleteBottomSheet(
                    onDismiss = {
                        onAction(MetalAction.OnCancelDelete)
                    },
                    OnDelete = {
                        onAction(MetalAction.OnDeleteConfirm)
                    }
                )
            }
            if(state.isAdding){
                AddMetal(
                    onDismiss = {
                        onAction(MetalAction.onDismiss)
                    },
                    onSaved = {
                        onAction(MetalAction.onSaved(it))

                    },
                    item = state.currentMetal ,
                    OnDelete = {
                        onAction(MetalAction.OnDelete)
                    },
                    OnSold = {
                        onAction(MetalAction.OnSold)
                    }
                )
            }

            if(state.metalList.isEmpty()){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center

                ){
                    Text(
                        text = "No Items",
                        style = MaterialTheme.typography.small,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)

            ) {
                items(state.metalList) { item ->
                    MetalItem(item,
                         OnEdit = {
                            onAction(MetalAction.onEdit(item))
                        }
                        )
                }
            }
        }
    }
}



