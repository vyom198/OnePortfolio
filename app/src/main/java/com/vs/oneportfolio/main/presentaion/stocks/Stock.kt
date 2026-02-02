package com.vs.oneportfolio.main.presentaion.stocks

import android.app.AlarmManager
import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vs.oneportfolio.R
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
import com.vs.oneportfolio.core.theme.ui.OnePortfolioTheme
import com.vs.oneportfolio.core.theme.ui.normal
import com.vs.oneportfolio.core.theme.ui.topBarTitle
import com.vs.oneportfolio.main.presentaion.home.HomeAction
import com.vs.oneportfolio.main.presentaion.model.StockUI
import com.vs.oneportfolio.main.presentaion.stocks.components.StockItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun StockRoot(
    viewModel: StockViewModel = koinViewModel(),
    onBackClick: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    StockScreen(
        state = state,
        onAction = viewModel::onAction,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen(
    state: StockState,
    onAction: (StockAction) -> Unit,
    onBackClick : () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

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
                    Text("Your Stocks",
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
            if(state.isAdding){
                ModalBottomSheet(
                    onDismissRequest = { onAction(StockAction.onDismiss) },
                    sheetState = sheetState,
                    modifier = Modifier.padding(
                        horizontal = 4.dp,
                        vertical = 16.dp
                    )

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight().padding(
                                horizontal = 16.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically

                    ){
                        OutlinedTextField(
                            value = state.text,
                            textStyle = MaterialTheme.typography.normal ,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = CardSurface,
                                unfocusedContainerColor = CardSurface,
                                focusedIndicatorColor = androidx.compose.ui.graphics.Color.White,
                                unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.White,
                            ),

                            onValueChange = {
                                onAction(StockAction.onTextChange(it))
                            },
                            modifier = Modifier.height(60.dp).width(
                                350.dp
                            ),
                            shape = RoundedCornerShape(16.dp),
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.input_hint),
                                    style = MaterialTheme.typography.normal,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                        alpha = 0.4f
                                    )
                                )
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                onAction(StockAction.onButtonClick)
                            },
                            modifier = Modifier.size(24.dp).background(
                                color = CardSurface ,
                                shape = CircleShape
                            ),
                            shape = CircleShape,

                        ) {
                            if(state.loading){
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 1.dp

                                    )
                            }else{
                                Icon(
                                    imageVector = Icons.Filled.Send,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary

                                )
                            }

                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)

            ) {
                  items(state.stocksList){ item->
                      StockItem(item)

                  }
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