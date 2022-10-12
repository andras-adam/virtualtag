package com.virtualtag.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.virtualtag.app.R

@Composable
fun Dialog(
    closeDialog: (() -> Unit)
) {
    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colors.secondaryVariant,
        titleContentColor = MaterialTheme.colors.secondaryVariant,
        onDismissRequest = { closeDialog() },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Outlined.ErrorOutline,
                    null, modifier = Modifier
                        .padding(top = 24.dp)
                        .size(72.dp),
                    tint = Color.Red
                )
                Text(
                    stringResource(R.string.nfc_sensor_error),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        },
        text = {
            Text(
                stringResource(R.string.nfc_sensor_error_description),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {},
        dismissButton = {
            PrimaryButton(
                text = "OK",
                onClick = { closeDialog() })
        }
    )
}