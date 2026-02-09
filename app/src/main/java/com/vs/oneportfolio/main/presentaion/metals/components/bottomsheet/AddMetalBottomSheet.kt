package com.vs.oneportfolio.main.presentaion.metals.components.bottomsheet

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.vs.oneportfolio.R
import com.vs.oneportfolio.core.database.metals.MetalEntity
import com.vs.oneportfolio.core.theme.ui.SkyBlueAccent
import com.vs.oneportfolio.core.theme.ui.label
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.normal
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets.AddAssetTextField
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets.DatePickerFieldToModal
import com.vs.oneportfolio.main.presentaion.metals.model.WeightUnitMapper
import com.vs.oneportfolio.main.presentaion.metals.model.convertToOz
import com.vs.oneportfolio.main.presentaion.metals.model.list
import com.vs.oneportfolio.main.presentaion.model.MetalUI
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMetal(
    onDismiss: () -> Unit,
    onSaved: (MetalEntity) -> Unit,
    item: MetalUI? = null
) {
    var enabled by retain { mutableStateOf(false) }
    var label by retain { mutableStateOf(item?.label ?: "") }
    var weight by retain { mutableStateOf(item?.weight ?: 0.0) }
    var unit by retain { mutableStateOf(item?.unit ?: 1) }
    var karat by retain { mutableIntStateOf(item?.karat ?: 0) }
    var unitString by retain { mutableStateOf(WeightUnitMapper.getString(unit)) }
    var weightString by retain {
        mutableStateOf(
            if (weight == 0.0) "" else weight.toString()
        )
    }
    var storageLocation by retain { mutableStateOf(item?.storageLocation ?: "") }
    var purchasePrice by retain { mutableDoubleStateOf(item?.purchasePrice ?: 0.0) }

    var karatString by retain {
        mutableStateOf(if (karat == 0) "" else karat.toString())
    }
    var purchasePriceString by retain {
        mutableStateOf(if (purchasePrice == 0.0) "" else purchasePrice.toString())
    }
    var purchaseDate by retain { mutableStateOf<Long?>(item?.purchaseDate) }


    val metal = MetalEntity(
        id = item?.id ?: 0,
        label = label,
        weight = weight ?: 0.0,
        unit = unit,
        karat = karat,
        purchasePrice = purchasePrice,
        purchaseDate = purchaseDate ?: Calendar.getInstance().timeInMillis,
        storageLocation = storageLocation,
        currentPrice = purchasePrice,
        category = null,
    )

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = rememberModalBottomSheetState(),
        modifier = Modifier.imePadding().animateContentSize()

    ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    ).weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {

                Text(
                    text = "Your ${item?.label ?: "Gold"}", style = MaterialTheme.typography.label,
                    color = MaterialTheme.colorScheme.onPrimary

                )
                Spacer(modifier = Modifier.height(10.dp))
                AddAssetTextField(
                    onTextChange = {
                        label = it
                    },
                    onClear = {
                        label = ""
                    },
                    text = label,
                    label = "Name",
                    hint = "e.g. Swiss PAMP Bar"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),

                    ) {
                    OutlinedTextField(
                        value = weightString,
                        textStyle = MaterialTheme.typography.normal,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                        ),
                        onValueChange = {
                            weightString = it
                            weight = convertToOz(it, unitString).weightInOz

                        },
                        label = {

                            Text(
                                text = "Weight",
                                style = MaterialTheme.typography.label,
                            )

                        },
                        suffix = {
                            Text(
                                text = unitString,
                                style = MaterialTheme.typography.label,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        maxLines = 1,
                        trailingIcon = {
                            Icon(
                                painter = painterResource(
                                    id = if (enabled) {
                                        R.drawable.arrow_up
                                    } else {
                                        R.drawable.arrow_down
                                    }
                                ),
                                tint = if (enabled) SkyBlueAccent else MaterialTheme.colorScheme.onSurfaceVariant,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        enabled = !enabled
                                    }
                            )


                        },
                        placeholder = {
                            Text(
                                text = "e.g. 100g",
                                style = MaterialTheme.typography.names,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    )

                }
                BoxWithConstraints {
                    DropdownMenu(
                        expanded = enabled,
                        onDismissRequest = { enabled = false },
                        modifier = Modifier
                            .wrapContentHeight()
                            .width(maxWidth)
                            .background(
                                color = MaterialTheme.colorScheme.surface
                            )
                            .clip(
                                shape = RoundedCornerShape(12.dp)
                            )
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = SkyBlueAccent
                                ),
                                shape = RoundedCornerShape(12.dp)

                            ),
                        offset = DpOffset(y = 2.dp, x = 0.dp),
                        shape = RoundedCornerShape(12.dp)

                    ) {
                        list.forEach { item ->

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        style = MaterialTheme.typography.label,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                },
                                onClick = {
                                    enabled = false
                                    unitString = item
                                    unit = WeightUnitMapper.getId(item)

                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                AddAssetTextField(
                    onTextChange = {
                        karatString = it
                        karat = it.toIntOrNull() ?: 0
                    },
                    onClear = {
                        karatString = ""
                    },
                    isNumber = true,

                    text = karatString,
                    label = "Karat",
                    hint = "e.g. 24k"


                )
                Spacer(modifier = Modifier.height(10.dp))
                AddAssetTextField(
                    onTextChange = {
                        purchasePriceString = it
                        purchasePrice = it.toDoubleOrNull() ?: 0.0
                    },
                    onClear = {
                        purchasePriceString = ""
                    },
                    isNumber = true,
                    text = purchasePriceString,
                    label = "Price",
                    hint = "e.g. $4999 "


                )
                Spacer(modifier = Modifier.height(10.dp))
                DatePickerFieldToModal(
                    selectedDate = purchaseDate,
                    label = "Purchase Date",
                    onSelectDate = {
                        purchaseDate = it
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                AddAssetTextField(
                    onTextChange = {
                        storageLocation = it
                    },
                    onClear = {
                        storageLocation = ""
                    },
                    text = storageLocation,
                    label = "Storage Location",
                    hint = "e.g. Vault "


                )

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = purchaseDate != null && purchasePrice != 0.0 && karat != 0 && label.isNotEmpty() && weight != null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SkyBlueAccent,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    onClick = {
                        onSaved(metal)
                    }) {
                    Text(
                        text = if (label.isEmpty()) "Save" else "Edit",
                        style = MaterialTheme.typography.names,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }


}



