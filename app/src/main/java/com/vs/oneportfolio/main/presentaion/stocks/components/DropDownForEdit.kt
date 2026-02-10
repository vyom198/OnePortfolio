package com.vs.oneportfolio.main.presentaion.stocks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.vs.oneportfolio.core.theme.ui.CardSurface
import com.vs.oneportfolio.core.theme.ui.EmeraldGreen
import com.vs.oneportfolio.core.theme.ui.LossRed
import com.vs.oneportfolio.core.theme.ui.OnePortfolioTheme
import com.vs.oneportfolio.core.theme.ui.small
import com.vs.oneportfolio.main.presentaion.metals.MetalAction

@Composable
fun EditDropDow(modifier: Modifier = Modifier,
                 onEdit : () -> Unit = {} ,
                 onDelete : () -> Unit = {},
                 onSold : () -> Unit = {},
                 isMenuEnabled : Boolean = false,
                 OnDismiss : () -> Unit


) {
    DropdownMenu(
        expanded = isMenuEnabled,
        offset = DpOffset(
            x = 250.dp,
            y = 0.dp
        ),
        onDismissRequest = OnDismiss,
        modifier = modifier.wrapContentSize().clip(
            shape = MaterialTheme.shapes.small
        ).border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurfaceVariant ,
            shape = MaterialTheme.shapes.small
        ).background(
            color = CardSurface
        ).padding(
            horizontal = 2.dp,
            vertical = 2.dp
        )
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    text = "Delete",
                    style = MaterialTheme.typography.small,
                    color = LossRed
                )
            },
            onClick = {
                onDelete()

            },


        )
        DropdownMenuItem(
            text = {
                Text(
                    text = "Sold",
                    style = MaterialTheme.typography.small,
                    color = EmeraldGreen
                )
            },
            onClick = {
                onSold()

            },

            )
        DropdownMenuItem(
            text = {
                Text(
                    text = "Edit Shares",
                    style = MaterialTheme.typography.small,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            onClick = {
                onEdit()
            },

            )

    }
}

