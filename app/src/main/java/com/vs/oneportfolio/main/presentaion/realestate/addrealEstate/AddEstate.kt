package com.vs.oneportfolio.main.presentaion.realestate.addrealEstate

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.vs.oneportfolio.R
import com.vs.oneportfolio.core.theme.ui.SkyBlueAccent
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.topBarTitle
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets.AddAssetTextField
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets.DatePickerFieldToModal
import com.vs.oneportfolio.main.presentaion.model.ObserveAsEvents
import com.vs.oneportfolio.main.presentaion.realestate.addrealEstate.AddEstateEvent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddEstateRoot(
    viewModel: AddEstateViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
           viewModel.onAction(AddEstateAction.onUriGet(uri))
        } else {

        }
    }
    ObserveAsEvents(
        flow = viewModel.events,
    ) { event ->
        when(event){
            is AddEstateEvent.onAddEvent -> {
                onBack()
            }
             is AddEstateEvent.LaunchPicker -> {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
             is AddEstateEvent.Saved -> {
                scope.launch {
                    snackbarHostState.showSnackbar("picture updated successfully")
                }
            }
        }
    }
    AddEstateScreen(
        state = state,
        onAction = viewModel::onAction,
        onBack = onBack,
        snackbarHostState = snackbarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEstateScreen(
    state: AddEstateState,
    onAction: (AddEstateAction) -> Unit,
    onBack: () -> Unit,
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
            ).imePadding(),
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
                                onBack()
                            },
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title = {
                    Text(state.screenTitle,
                        style = MaterialTheme.typography.topBarTitle)

                }
            )

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues).verticalScroll(
                    state = androidx.compose.foundation.rememberScrollState()
                ).animateContentSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth().height(200.dp)
                    .clip(
                        shape = RoundedCornerShape(12.dp)
                    ).border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = SkyBlueAccent,
                        ) ,
                        shape = RoundedCornerShape(12.dp)

                    ).padding(4.dp
                    ),

                contentAlignment = Alignment.Center
            ){

                AsyncImage(
                    model = state.properImg,
                    contentDescription = null,
                    fallback = painterResource(R.drawable.img_1),
                    modifier = Modifier.fillMaxSize()
                        .clip(
                            shape = RoundedCornerShape(12.dp)

                        ).padding(2.dp) ,

                    contentScale = ContentScale.FillBounds

                )

            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    onAction(AddEstateAction.onChangeAvtar)
                },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = SkyBlueAccent),
                modifier = Modifier
                    .height(44.dp).align(
                        Alignment.CenterHorizontally
                    ),
                shape = RoundedCornerShape(12.dp),

            ) {
                Text(
                    text = "Change",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.names
                )
            }
            Spacer(modifier = Modifier.height(10.dp))

               AddAssetTextField(
                onTextChange = {
                   onAction(AddEstateAction.OnFieldUpdate(EstateField.NAME,it))
                },
                onClear = {
                    onAction(AddEstateAction.OnFieldCancel(EstateField.NAME))
                },
                text = state.propertyName,
                hint = "e.g. Reliance Housing", label = "name"

              )
            Spacer(modifier = Modifier.height(10.dp))
            AddAssetTextField(
                onTextChange = {
                    onAction(AddEstateAction.OnFieldUpdate(EstateField.TYPE,it))
                },
                onClear = {
                    onAction(AddEstateAction.OnFieldCancel(EstateField.TYPE))

                },

                text = state.propertyType,
                hint = "e.g. Shop" ,
                label = "type"

            )

            Spacer(modifier = Modifier.height(10.dp))
            AddAssetTextField(
                onTextChange = {
                    onAction(AddEstateAction.OnFieldUpdate(EstateField.PRICE,it))
                },
                isNumber = true,
                onClear = {
                    onAction(AddEstateAction.OnFieldCancel(EstateField.PRICE))
                },
                text = state.purchasePrice,
                hint = "e.g. $76,000 ",
                label = "price"
            )
            Spacer(modifier = Modifier.height(10.dp))
            AddAssetTextField(
                onTextChange = {
                    onAction(AddEstateAction.OnFieldUpdate(EstateField.YIELD,it))
                },
                isNumber = true,
                onClear = {
                    onAction(AddEstateAction.OnFieldCancel(EstateField.YIELD))
                },
                text = state.yieldRate,
                hint = "e.g. 7.6 %",
                label = "yield"
            )
            Spacer(modifier = Modifier.height(10.dp))
            DatePickerFieldToModal(
                selectedDate =  state.purchaseDate ,
                label = "purchase date",
                onSelectDate = {
                 onAction(AddEstateAction.onPurchaseDateChange(it))
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            AddAssetTextField(
                onTextChange = {
                    onAction(AddEstateAction.OnFieldUpdate(EstateField.ADDRESS,it))
                },
                onClear = {
                    onAction(AddEstateAction.OnFieldCancel(EstateField.ADDRESS))
                },
                text = state.address,
                hint = "e.g. London 12 street near hotel",
                label = "Address"

            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .clip(
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.background,
                    ).border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = SkyBlueAccent
                        ) ,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 14.dp)
                    .selectableGroup()
            ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .selectable(
                                selected = state.isRented,
                                onClick = {
                                    onAction(AddEstateAction.onRentedChange(state.isRented))
                                },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))
                        RadioButton(
                            colors = RadioButtonColors(
                                unselectedColor = MaterialTheme.colorScheme.onPrimary,
                                selectedColor = SkyBlueAccent,
                                disabledSelectedColor = MaterialTheme.colorScheme.background,
                                disabledUnselectedColor = MaterialTheme.colorScheme.background,
                            ),
                            selected = state.isRented,
                            onClick = null,

                            )
                        Text(
                            text = "Rented",
                            style = MaterialTheme.typography.names,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .selectable(
                            selected = state.hasMortgage,
                            onClick = {
                                onAction(AddEstateAction.onMortgageChange(state.hasMortgage))
                            },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.height(5.dp))
                    RadioButton(
                        colors = RadioButtonColors(
                            unselectedColor = MaterialTheme.colorScheme.onPrimary,
                            selectedColor = SkyBlueAccent,
                            disabledSelectedColor = MaterialTheme.colorScheme.background,
                            disabledUnselectedColor = MaterialTheme.colorScheme.background,
                        ),
                        selected = state.hasMortgage,
                        onClick = null,

                        )
                    Text(
                        text = "Mortgaged",
                        style = MaterialTheme.typography.names,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }



            }
            if(state.isRented){
                Spacer(modifier = Modifier.height(10.dp))
                AddAssetTextField(
                    onTextChange = {
                        onAction(AddEstateAction.OnFieldUpdate(EstateField.RENT,it))
                    },
                    isNumber = true,
                    onClear = {
                        onAction(AddEstateAction.OnFieldCancel(EstateField.RENT))
                    },
                    text = state.monthlyRent,
                    hint = "e.g. $1200",
                    label = "Rent"

                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            if(state.hasMortgage){
                AddAssetTextField(
                    onTextChange = {
                        onAction(AddEstateAction.OnFieldUpdate(EstateField.MORTGAGE_BAL,it))
                    },
                    isNumber = true,
                    onClear = {
                        onAction(AddEstateAction.OnFieldCancel(EstateField.MORTGAGE_BAL))
                    },
                    text = state.mortgageBalance,
                    hint = "e.g. $100,000",
                    label = "Mortgage Balance"
                )
                Spacer(modifier = Modifier.height(10.dp))
                AddAssetTextField(
                    onTextChange = {
                        onAction(AddEstateAction.OnFieldUpdate(EstateField.MORTGAGE_PAY,it))
                    },
                    isNumber = true,
                    onClear = {
                        onAction(AddEstateAction.OnFieldCancel(EstateField.MORTGAGE_PAY))
                    },
                    text = state.mortgagePayment,
                    hint = "e.g. $10,000",
                    label = "Mortgage Payment"
                )

            }
            Spacer(modifier = Modifier.height(10.dp))
            DatePickerFieldToModal(
                selectedDate =  state.taxDueDate ,
                label = "Tax Due Date",
                onSelectDate = {
                    onAction(AddEstateAction.onTaxDateChange(it))
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                enabled = state.purchaseDate != null,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = SkyBlueAccent
                ),
                onClick = {
                    onAction(AddEstateAction.OnSave)
                }
            ) {
                Text(text = if(state.screenTitle == "Add Your Real Estate")"Add to Assets" else "Edit" ,
                    style = MaterialTheme.typography.names,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }




        }


    }
}

//@Preview
//@Composable
//private fun Preview() {
//    OnePortfolioTheme {
//        AddEstateScreen(
//            state = AddEstateState(),
//            onAction = {}
//        )
//    }
//}