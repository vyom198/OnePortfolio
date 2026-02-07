package com.vs.oneportfolio.main.presentaion.home

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
 import androidx.compose.foundation.layout.wrapContentHeight
 import androidx.compose.foundation.layout.wrapContentSize
 import androidx.compose.foundation.lazy.LazyColumn
 import androidx.compose.foundation.shape.RoundedCornerShape
 import androidx.compose.material.icons.Icons
 import androidx.compose.material.icons.filled.KeyboardArrowRight
 import androidx.compose.material.icons.filled.TrendingDown
 import androidx.compose.material.icons.filled.TrendingUp
 import androidx.compose.material3.CenterAlignedTopAppBar
 import androidx.compose.material3.ExperimentalMaterial3Api
 import androidx.compose.material3.Icon
 import androidx.compose.material3.MaterialTheme
 import androidx.compose.material3.Scaffold
 import androidx.compose.material3.Text
 import androidx.compose.material3.TopAppBarDefaults
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.getValue
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.draw.clip
 import androidx.compose.ui.text.font.FontWeight
 import androidx.compose.ui.unit.dp
 import androidx.compose.ui.unit.sp
 import androidx.lifecycle.compose.collectAsStateWithLifecycle
 import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
 import com.vs.oneportfolio.core.theme.ui.LossRed
 import com.vs.oneportfolio.core.theme.ui.SkyBlueAccent
 import com.vs.oneportfolio.core.theme.ui.normal
 import com.vs.oneportfolio.core.theme.ui.small
 import com.vs.oneportfolio.core.theme.ui.topBarTitle
 import com.vs.oneportfolio.main.mapper.formats
 import com.vs.oneportfolio.main.mapper.toCommaString
 import org.koin.androidx.compose.koinViewModel
 import kotlin.math.absoluteValue

@Composable
fun HomeRoot(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToStock: () -> Unit ,
    onNavigateToCrypto : () -> Unit ,
    onNavigateToFA : () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    HomeScreen(
        state = state,
        onAction = viewModel::onAction,
        onNavigateToStock = onNavigateToStock ,
        onNavigateToCrypto = onNavigateToCrypto,
        onNavigateToFA = onNavigateToFA
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    onNavigateToStock : () -> Unit,
    onNavigateToFA : () -> Unit ,
    onNavigateToCrypto : () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text("Investments",
                        style = MaterialTheme.typography.topBarTitle,
                        color = MaterialTheme.colorScheme.onPrimary

                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(
                horizontal = 16.dp
            ),


        ) {
            Column(
                modifier = Modifier.fillMaxWidth().clip(
                    shape = RoundedCornerShape(16.dp)
                ).background(
                    color = MaterialTheme.colorScheme.tertiary
                ).padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
                 verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Row (
                    modifier = Modifier.fillMaxWidth() ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Portfolio Value",
                        style = MaterialTheme.typography.normal,
                        color = SkyBlueAccent.copy(
                            0.9f
                        )


                    )
                    Icon(
                        imageVector = if (state.isPositive) Icons.Filled.TrendingUp else Icons.Filled.TrendingDown,
                        contentDescription = null,
                        modifier = Modifier.size(
                            34.dp
                        ),
                        tint = if (state.isPositive) EmeraldGreen else LossRed

                    )

                }
                Text(
                    text = "$${state.totalPortfolioValue.toCommaString()}",
                    style = MaterialTheme.typography.normal.copy(
                        fontWeight = FontWeight.SemiBold ,
                        fontSize = 30.sp ,
                        lineHeight = 36.sp,

                    )
                )
                Text(
                    text = "${if(state.isPositive){"+"} else {"-"}}$${state.absPnL.toCommaString()}(${state.pnlPercentage.absoluteValue.formats()}%)",
                    style = MaterialTheme.typography.normal.copy(
                        fontWeight = FontWeight.SemiBold ,
                        fontSize = 16.sp ,
                        lineHeight = 20.sp,
                        color = if(state.isPositive) EmeraldGreen else LossRed

                    )
                )
                Text(
                    text = "Total Invested: $${state.totalInvestedInAssets.toCommaString()}",
                    style = MaterialTheme.typography.normal.copy(
                        fontWeight = FontWeight.SemiBold ,
                        fontSize = 14.sp ,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant

                    )
                )

            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Categories",
                style = MaterialTheme.typography.normal,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize() ,
                verticalArrangement = Arrangement.spacedBy(10.dp
                )
            ) {

                item{
                    Column(
                        modifier = Modifier.fillMaxWidth().clip(
                            shape = RoundedCornerShape(16.dp)
                        ).background(
                            color = MaterialTheme.colorScheme.surface

                        ).padding(
                            horizontal = 16.dp,
                            vertical = 16.dp

                        ),
                    ) {
                        Text(
                            text = "Stocks & ETFs",
                            style = MaterialTheme.typography.normal.copy(
                                fontWeight = FontWeight.W600,
                                fontSize = 18.sp,
                                lineHeight = 26.sp,
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.wrapContentSize()) {
                            Text(
                                text = "Invested: ",
                                style = MaterialTheme.typography.small,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "$${state.totalInvested.toCommaString()}",
                                style = MaterialTheme.typography.small,
                                color = MaterialTheme.colorScheme.onSurfaceVariant

                            )


                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = if (state.isTradePositive) {
                                    "+$${state.tradeabs.toCommaString()}"
                                } else {
                                    "-$${state.tradeabs.toCommaString()}"
                                },
                                style = MaterialTheme.typography.small,
                                color = if (state.isTradePositive) EmeraldGreen else LossRed

                            )


                        }
                       Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Text(
                                text = "Total Stocks: ${state.totalItemsInTrade}",
                                style = MaterialTheme.typography.small,
                                color = MaterialTheme.colorScheme.onSurfaceVariant

                            )

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            modifier = Modifier.size(
                                24.dp
                            ).clickable {
                                onNavigateToStock()
                            },
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary

                        )
                    }

                    }
                }

                item{
                    Column(
                        modifier = Modifier.fillMaxWidth().clip(
                            shape = RoundedCornerShape(16.dp)
                        ).background(
                            color = MaterialTheme.colorScheme.surface

                        ).padding(
                            horizontal = 16.dp,
                            vertical = 16.dp

                        ),
                    ) {
                        Text(
                            text = "Crypto's",
                            style = MaterialTheme.typography.normal.copy(
                                fontWeight = FontWeight.W600,
                                fontSize = 18.sp,
                                lineHeight = 26.sp,
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.wrapContentSize()) {
                            Text(
                                text = "Invested: ",
                                style = MaterialTheme.typography.small,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "$${state.totalInvestedInCrypto.toCommaString()}",
                                style = MaterialTheme.typography.small,
                                color = MaterialTheme.colorScheme.onSurfaceVariant

                            )


                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = if (state.isCryptoPositive) {
                                    "+$${state.cryptoabs.toCommaString()}"
                                } else {
                                    "-$${state.cryptoabs.toCommaString()}"
                                },
                                style = MaterialTheme.typography.small,
                                color = if (state.isCryptoPositive) EmeraldGreen else LossRed

                            )


                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Text(
                                text = "Total Crypto's: ${state.totalItemsInCrypto}",
                                style = MaterialTheme.typography.small,
                                color = MaterialTheme.colorScheme.onSurfaceVariant

                            )

                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                modifier = Modifier.size(
                                    24.dp
                                ).clickable {
                                    onNavigateToCrypto()
                                },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary

                            )
                        }

                    }
                }

                item{
                    Column(
                        modifier = Modifier.fillMaxWidth().clip(
                            shape = RoundedCornerShape(16.dp)
                        ).background(
                            color = MaterialTheme.colorScheme.surface

                        ).padding(
                            horizontal = 16.dp,
                            vertical = 16.dp

                        ),
                    ) {
                        Text(
                            text = "Fixed Income Assets",
                            style = MaterialTheme.typography.normal.copy(
                                fontWeight = FontWeight.W600,
                                fontSize = 18.sp,
                                lineHeight = 26.sp,
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.wrapContentSize()) {
                            Text(
                                text = "Invested: ",
                                style = MaterialTheme.typography.small,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "$${state.totalInvestedInFA.toCommaString()}",
                                style = MaterialTheme.typography.small,
                                color = MaterialTheme.colorScheme.onSurfaceVariant

                            )


                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = if (state.isAssetsPositive) {
                                    "+$${state.assetsAbs.toCommaString()}"
                                } else {
                                    "-$${state.assetsAbs.toCommaString()}"
                                },
                                style = MaterialTheme.typography.small,
                                color = if (state.isAssetsPositive) EmeraldGreen else LossRed

                            )


                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            Text(
                                text = "Total Assets: ${state.totalItemsinFA}",
                                style = MaterialTheme.typography.small,
                                color = MaterialTheme.colorScheme.onSurfaceVariant

                            )

                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                modifier = Modifier.size(
                                    24.dp
                                ).clickable {
                                    onNavigateToFA()
                                },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary

                            )
                        }

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
//        HomeScreen(
//            state = HomeState(),
//            onAction = {}
//        )
//    }
//}