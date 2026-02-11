package com.vs.oneportfolio.main.presentaion.portfoliohealth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vs.oneportfolio.core.theme.ui.topBarTitle
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.EmptyAnanlysisScreen
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.PortfolioAnalysisContent
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.PortfolioAnalysisLoadingView
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.PulseFAB
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PortfolioAnalysisRoot(
    viewModel: PortfolioAnalysisViewModel = koinViewModel(),
    onBackClick : () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PortfolioAnalysisScreen(
        state = state,
        onAction = viewModel::onAction,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioAnalysisScreen(
    state: PortfolioAnalysisState,
    onAction: (PortfolioAnalysisAction) -> Unit,
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
        floatingActionButton = {
            PulseFAB(
                onClick = { onAction(PortfolioAnalysisAction.OnClickFab) }
            )
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
                title = {
                    Text(
                        "Portfolio Health",
                        style = MaterialTheme.typography.topBarTitle
                    )

                }
            )

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {

            if(state.loading){
                PortfolioAnalysisLoadingView()
            }
            if (state.data == null) {
               EmptyAnanlysisScreen()
            }else{
                PortfolioAnalysisContent(state.data)
            }
        }
    }
}



