package com.virtualtag.app.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Composable
fun Dialog(
    closeDialog: (() -> Unit),
    title: @Composable () -> Unit,
    description: @Composable () -> Unit,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
) {
    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colors.secondaryVariant,
        titleContentColor = MaterialTheme.colors.secondaryVariant,
        onDismissRequest = { closeDialog() },
        title = {
            title()
        },
        text = {
            description()
        },
        confirmButton = { confirmButton() },
        dismissButton = {
            dismissButton()
        }
    )
}
