package com.virtualtag.app.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun Logo(modifier: Modifier?) {
    Row(modifier = modifier ?: Modifier) {
        Text(
            "VIRTUAL",
            color = MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.h1,
            fontWeight = FontWeight.Light
        )
        Text(
            "TAG",
            color = MaterialTheme.colors.primaryVariant,
            style = MaterialTheme.typography.h1
        )
    }
}