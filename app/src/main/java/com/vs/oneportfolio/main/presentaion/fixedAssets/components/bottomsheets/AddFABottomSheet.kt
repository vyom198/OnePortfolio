package com.vs.oneportfolio.main.presentaion.fixedAssets.components.bottomsheets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.vs.oneportfolio.R
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.DeepNavyBg
import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
import com.vs.oneportfolio.core.theme.ui.SkyBlueAccent
import com.vs.oneportfolio.core.theme.ui.label
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.normal
import com.vs.oneportfolio.main.presentaion.crypto.components.bottomsheets.CoinItem
import com.vs.oneportfolio.main.presentaion.fixedAssets.FixedAssetsState
import com.vs.oneportfolio.main.presentaion.fixedAssets.components.model.PayOutType
import okio.inMemorySocketPair
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddfixedAsset(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    //state: FixedAssetsState,
) {

    var depositName by remember { mutableStateOf("") }
    var InstitutionName by remember { mutableStateOf("") }
    var amtPrincipal by remember { mutableStateOf("") }
    var interestRatePercent by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf(PayOutType.MONTHLY) }
    var isCumulative by remember { mutableStateOf(false) }
    var OpenDate by remember { mutableStateOf<Long?>(null) }
    var MaturityDate by remember { mutableStateOf<Long?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val radioOption = listOf(
        "Compounding", "Income"
    )
    var selectedOption by remember { mutableStateOf(radioOption[0]) }
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = rememberModalBottomSheetState(),
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
        ) {

            Text(
                text = "Your Asset", style = MaterialTheme.typography.label,
                color = MaterialTheme.colorScheme.onPrimary

            )
            Spacer(modifier = Modifier.height(10.dp))
            AddAssetTextField(
                onTextChange = {
                    depositName = it
                },
                onClear = {
                    depositName = ""
                },
                text = depositName,
                label = "Scheme Name",
                hint = "e.g. HDFC Tax Saver FD"


            )
            Spacer(modifier = Modifier.height(10.dp))
            AddAssetTextField(
                onTextChange = {
                    InstitutionName = it
                },
                onClear = {
                    InstitutionName = ""
                },
                text = InstitutionName,
                label = "Institution Name",
                hint = "e.g. HDFC bank"


            )
            Spacer(modifier = Modifier.height(10.dp))
            AddAssetTextField(
                onTextChange = {
                    amtPrincipal = it
                },
                onClear = {
                    amtPrincipal = ""
                },
                isNumber = true,

                text = amtPrincipal,
                label = "Principal Amount",
                hint = "e.g. $10,000"


            )
            Spacer(modifier = Modifier.height(10.dp))
            AddAssetTextField(
                onTextChange = {
                    interestRatePercent = it
                },
                isNumber = true,

                onClear = {
                    interestRatePercent = ""
                },
                text = interestRatePercent,
                label = "Yield",
                hint = "e.g. 7.6 %"


            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        .padding(horizontal = 12.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Income PayOut Type", style = MaterialTheme.typography.label,
                        color = MaterialTheme.colorScheme.background
                    )

                    Icon(
                        painter = painterResource(
                            id = if (expanded) {
                                R.drawable.arrow_up
                            } else {
                                R.drawable.arrow_down
                            }
                        ),
                        tint = DeepNavyBg,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            expanded = !expanded
                        }
                    )
                }

                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = selected.value, style = MaterialTheme.typography.names,
                    color = MaterialTheme.colorScheme.background
                )
                Spacer(modifier = Modifier.height(19.dp))
            }


            BoxWithConstraints {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(maxWidth)
                        .background(
                            color = MaterialTheme.colorScheme.onPrimary,
                        ),
                    offset = DpOffset(y = 2.dp, x = 0.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    PayOutType.entries.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = item.value,
                                    style = MaterialTheme.typography.names,
                                    color = MaterialTheme.colorScheme.background
                                )
                            },
                            onClick = {
                                selected = item
                                expanded = false

                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }

                }

            }

            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .clip(
                        shape = RoundedCornerShape(12.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    .padding(horizontal = 12.dp, vertical = 14.dp)
                    .selectableGroup()
            ) {
                Text(
                    text = "Asset Type", style = MaterialTheme.typography.label,
                    color = MaterialTheme.colorScheme.background
                )
                Spacer(modifier = Modifier.height(8.dp))
                radioOption.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    selectedOption = text
                                },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))
                        RadioButton(
                            colors = RadioButtonColors(
                                unselectedColor = MaterialTheme.colorScheme.background,
                                selectedColor = SkyBlueAccent,
                                disabledSelectedColor = MaterialTheme.colorScheme.background,
                                disabledUnselectedColor = MaterialTheme.colorScheme.background,
                            ),
                            selected = (text == selectedOption),
                            onClick = null,

                            )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.names,
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            DatePickerFieldToModal(
                selectedDate = OpenDate,
                label = "Open Date",
                onSelectDate = {
                    OpenDate = it
                }
            )

        }
    }
}


@Composable
fun AddAssetTextField(
    onTextChange: (String) -> Unit,
    onClear: (() -> Unit)? = null,
    text: String,
    label: String,
    isNumber: Boolean = false,
    hint: String
) {


    OutlinedTextField(
        value = text,
        textStyle = MaterialTheme.typography.normal,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            focusedIndicatorColor = CardSurface,
            unfocusedIndicatorColor = CardSurface,
            focusedTextColor = MaterialTheme.colorScheme.background,
            unfocusedTextColor = MaterialTheme.colorScheme.background,
            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedLabelColor = MaterialTheme.colorScheme.background,
            cursorColor = MaterialTheme.colorScheme.background,
        ),
        keyboardOptions = if (isNumber) KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
        ) else KeyboardOptions.Default,
        onValueChange = {
            onTextChange(it)

        },

        label = {

            Text(
                text = label,
                style = MaterialTheme.typography.label,
            )

        },
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        maxLines = 1,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = DeepNavyBg,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onClear?.invoke()
                    }
            )
        },
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.names,
                color = MaterialTheme.colorScheme.background
            )
        }
    )

}

@Composable
fun DatePickerFieldToModal(
    modifier: Modifier = Modifier,
    selectedDate: Long? = null, label: String, onSelectDate: (Long?) -> Unit


) {
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedDate?.let { convertMillisToDate(it) } ?: "",
        onValueChange = { },
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.label,
            )
        },
        placeholder = {
            Text(
                text = "MM/DD/YYYY",
                style = MaterialTheme.typography.names,
                color = MaterialTheme.colorScheme.background
            )
        },
        trailingIcon = {
            Icon(
                Icons.Default.DateRange, contentDescription = "Select date",
                modifier = Modifier
                    .size(24.dp)
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )

    if (showModal) {
        DatePickerModal(
            onDateSelected = {
                onSelectDate(
                    it
                )
            },
            onDismiss = { showModal = false }
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

