package com.vs.oneportfolio.main.presentaion.portfoliohealth.history

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.vs.oneportfolio.R
import com.vs.oneportfolio.core.database.portfoliohealth.PortfolioHealthEntity
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.small
import com.vs.oneportfolio.core.theme.ui.topBarTitle
import com.vs.oneportfolio.main.mapper.toCommaString
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets.convertMillisToDate
import com.vs.oneportfolio.main.presentaion.portfoliohealth.PortfolioAnalysisAction
import com.vs.oneportfolio.main.presentaion.portfoliohealth.components.PulseFAB
import com.vs.oneportfolio.main.presentaion.realestate.history.SoldEstateItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun PortfolioHealthHistoryRoot(
    viewModel: PortfolioHealthHistoryViewModel = koinViewModel(),
    onBackClick : () -> Unit,
    onNavigateToDetail : (Long) -> Unit

) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PortfolioHealthHistoryScreen(
        state = state,
        onAction = viewModel::onAction,
        onBackClick = onBackClick,
        onNavigateToDetail = onNavigateToDetail
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioHealthHistoryScreen(
    state: PortfolioHealthHistoryState,
    onAction: (PortfolioHealthHistoryAction) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToDetail: (Long) -> Unit
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
                    Text(
                        "Saved Portfolio Reports",
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
             if(state.data.isEmpty()){
                 Box(
                     modifier = Modifier.fillMaxSize(),
                     contentAlignment = androidx.compose.ui.Alignment.Center
                 ) {
                     Text("No Reports Exit")
                 }
             }else{
                 LazyColumn(
                     modifier = Modifier.fillMaxSize().animateContentSize(),
                     verticalArrangement = Arrangement.spacedBy(8.dp)
                 ) {
                     items(state.data) { item ->
                         PortfolioHistoryItem(item){
                             onNavigateToDetail(item.id)
                         }
                     }
                 }
             }
         }
    }
}

@Composable
fun PortfolioHistoryItem(item: PortfolioHealthEntity,
                         onArrowClick : ()->Unit
){
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
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = "Health Score: ",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = "${item.healthScore}/100",
                style = MaterialTheme.typography.small,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = "Overall Rating: ",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = item.overallRating,
                style = MaterialTheme.typography.small,
                color = MaterialTheme.colorScheme.onPrimary,
            )

        }
        Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Generated On: ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text =  convertMillisToDate( item.timestamp),
                    maxLines = 2,
                    style = MaterialTheme.typography.small,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    modifier = Modifier.size(
                        24.dp
                    ).clickable {
                        onArrowClick()
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary

                )
            }



    }
}

