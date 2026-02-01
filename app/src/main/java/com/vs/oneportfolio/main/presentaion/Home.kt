 package com.vs.oneportfolio.main.presentaion

 import androidx.compose.foundation.background
 import androidx.compose.foundation.layout.Arrangement
 import androidx.compose.foundation.layout.Column
 import androidx.compose.foundation.layout.fillMaxSize
 import androidx.compose.foundation.layout.fillMaxWidth
 import androidx.compose.foundation.layout.height
 import androidx.compose.foundation.layout.padding
 import androidx.compose.foundation.layout.size
 import androidx.compose.foundation.layout.wrapContentHeight
 import androidx.compose.foundation.lazy.LazyColumn
 import androidx.compose.foundation.lazy.items
 import androidx.compose.material3.Button
 import androidx.compose.material3.CircularProgressIndicator
 import androidx.compose.material3.OutlinedTextField
 import androidx.compose.material3.Scaffold
 import androidx.compose.material3.Text
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.getValue
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.graphics.Color
 import androidx.compose.ui.unit.dp
 import androidx.lifecycle.compose.collectAsStateWithLifecycle
 import org.koin.androidx.compose.koinViewModel

 @Composable
fun HomeRoot(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(
                horizontal = 16.dp
            ) ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(state.loading){
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp)
                )
            }else{
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,


                ){
                    items(state.stocksList) {item->
                       Column(
                           modifier = Modifier.fillMaxWidth().height(200.dp)
                       ) {
                            Text(
                                text = item.ticker

                            )
                           Text(
                               text = item.name
                           )
                           Text(
                               text = item.quantity.toString()
                           )
                           Text(
                               text = item.averagePrice.toString()
                           )
                           Text(
                               text = item.currentPrice.toString()
                           )

                       }
                    }
                }
            }

            OutlinedTextField(
                value = state.text ,
                onValueChange = {
                    onAction(HomeAction.onTextChange(it))
                }
            )

            Button(
                onClick = {
                    onAction(HomeAction.onButtonClick)
                }
            ) {
                Text(
                    text = "Insert"
                )

            }
        }

    }
}

//@Preview
//@Composable
//private fun Preview() {
//    OnePortfolioTheme {
//        HomeScreen(
//            state = HomeState(),
//            onAction = {}
//        )
//    }
//}