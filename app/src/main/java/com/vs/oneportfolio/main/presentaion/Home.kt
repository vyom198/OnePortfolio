 package com.vs.oneportfolio.main.presentaion

 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.getValue
 import androidx.lifecycle.compose.collectAsStateWithLifecycle
 import androidx.lifecycle.viewmodel.compose.viewModel
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