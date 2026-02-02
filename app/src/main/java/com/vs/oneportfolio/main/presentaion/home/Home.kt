package com.vs.oneportfolio.main.presentaion.home

 import android.R.attr.fontWeight
 import androidx.compose.foundation.background
 import androidx.compose.foundation.border
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
 import androidx.compose.foundation.layout.wrapContentSize
 import androidx.compose.foundation.lazy.LazyColumn
 import androidx.compose.foundation.lazy.items
 import androidx.compose.foundation.shape.CircleShape
 import androidx.compose.foundation.shape.RoundedCornerShape
 import androidx.compose.material.icons.Icons
 import androidx.compose.material.icons.filled.Add
 import androidx.compose.material.icons.filled.ArrowForward
 import androidx.compose.material.icons.filled.KeyboardArrowRight
 import androidx.compose.material3.Button
 import androidx.compose.material3.CenterAlignedTopAppBar
 import androidx.compose.material3.CircularProgressIndicator
 import androidx.compose.material3.ExperimentalMaterial3Api
 import androidx.compose.material3.Icon
 import androidx.compose.material3.IconButton
 import androidx.compose.material3.MaterialTheme
 import androidx.compose.material3.OutlinedTextField
 import androidx.compose.material3.Scaffold
 import androidx.compose.material3.Text
 import androidx.compose.material3.TopAppBarDefaults
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.getValue
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.draw.clip
 import androidx.compose.ui.text.font.FontWeight
 import androidx.compose.ui.tooling.preview.Preview
 import androidx.compose.ui.unit.dp
 import androidx.compose.ui.unit.sp
 import androidx.lifecycle.compose.collectAsStateWithLifecycle
 import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
 import com.vs.oneportfolio.core.theme.ui.SkyBlueAccent
 import com.vs.oneportfolio.core.theme.ui.normal
 import com.vs.oneportfolio.core.theme.ui.topBarTitle
 import org.koin.androidx.compose.koinViewModel

 @Composable
fun HomeRoot(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToStock: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    HomeScreen(
        state = state,
        onAction = viewModel::onAction,
        onNavigateToStock = onNavigateToStock
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
    onNavigateToStock : () -> Unit
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

                Text(
                    text = "Total Portfolio Value",
                    style = MaterialTheme.typography.normal,
                        color = SkyBlueAccent.copy(
                            0.9f
                        )


                )
                Text(
                    text = "$${state.totalPortfolioValue}",
                    style = MaterialTheme.typography.normal.copy(
                        fontWeight = FontWeight.SemiBold ,
                        fontSize = 30.sp ,
                        lineHeight = 36.sp,

                    )
                )
                Text(
                    text = "+$2550.00(14.17%)",
                    style = MaterialTheme.typography.normal.copy(
                        fontWeight = FontWeight.SemiBold ,
                        fontSize = 16.sp ,
                        lineHeight = 20.sp,
                        color = EmeraldGreen

                    )
                )
                Text(
                    text = "Total Invested: $18,000",
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
                modifier = Modifier.fillMaxSize()
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
                            text = "Stocks",
                            style = MaterialTheme.typography.normal.copy(
                                fontWeight = FontWeight.W600,
                                fontSize = 18.sp ,
                                lineHeight = 26.sp,
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.wrapContentSize()) {
                            Text(
                                text = "Invested: ",
                                style = MaterialTheme.typography.normal.copy(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp ,
                                    lineHeight = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Text(
                                text = "$15,000",
                                style = MaterialTheme.typography.normal.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp ,
                                    lineHeight = 20.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )


                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "+$15000",
                                style = MaterialTheme.typography.normal.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp ,
                                    lineHeight = 20.sp,
                                    color = EmeraldGreen
                                )
                            )
                            Spacer(modifier = Modifier.width(20.dp))
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    modifier = Modifier.size(
                                        24.dp
                                    ).clickable{
                                        onNavigateToStock()
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