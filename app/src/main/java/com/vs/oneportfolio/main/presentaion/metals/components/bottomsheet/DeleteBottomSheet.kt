package com.vs.oneportfolio.main.presentaion.metals.components.bottomsheet

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.room.Delete
import com.vs.oneportfolio.core.database.metals.MetalEntity
import com.vs.oneportfolio.core.theme.ui.LossRed
import com.vs.oneportfolio.core.theme.ui.SkyBlueAccent
import com.vs.oneportfolio.core.theme.ui.normal
import com.vs.oneportfolio.main.presentaion.metals.model.WeightUnitMapper
import com.vs.oneportfolio.main.presentaion.model.MetalUI
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteBottomSheet(
    onDismiss:()-> Unit,
    OnDelete : ()-> Unit ,

) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        containerColor = MaterialTheme.colorScheme.background,
        sheetState = rememberModalBottomSheetState(),
        modifier = Modifier.fillMaxWidth().width(120.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxWidth().width(120.dp).padding(
                horizontal = 16.dp,
                vertical = 16.dp
            )
        ) {
            Text(
                text = "Are you sure you want to delete this item?",
                style = MaterialTheme.typography.normal,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                horizontalArrangement = Arrangement.Center,

                verticalAlignment = Alignment.CenterVertically

            ) {
                OutlinedButton(
                    onClick = {
                        onDismiss()
                    },
                    shape = CircleShape,
                    modifier = Modifier
                ) {
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.normal,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {
                        OnDelete()
                    },
                    modifier = Modifier
                ) {
                    Text(
                        text = "Delete",
                        style = MaterialTheme.typography.normal,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}