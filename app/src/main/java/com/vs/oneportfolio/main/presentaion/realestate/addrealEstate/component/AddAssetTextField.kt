package com.vs.oneportfolio.main.presentaion.realestate.addrealEstate.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.vs.oneportfolio.core.theme.ui.SkyBlueAccent
import com.vs.oneportfolio.core.theme.ui.names
import com.vs.oneportfolio.core.theme.ui.normal

@Composable
fun RealTextField(
    onTextChange: (String) -> Unit,
    onClear: (() -> Unit)? = null,
    text: String,
    isNumber: Boolean = false,
    hint: String
) {


    OutlinedTextField(
        value = text,
        textStyle = MaterialTheme.typography.normal,
        keyboardOptions = if (isNumber) KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
        ) else KeyboardOptions.Default,
        onValueChange = {
            onTextChange(it)
        },

        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        maxLines = 1,
        trailingIcon = if(text.isNotEmpty()){{
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = SkyBlueAccent,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onClear?.invoke()
                    }
            )
        }} else null ,
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.names,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    )

}