package com.virtualtag.app.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier

            .padding(top = 8.dp, bottom = 8.dp),
        contentPadding = PaddingValues(all = 16.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 2.dp)
    ) {
        Spacer(modifier = Modifier.weight(1.0f))
        Text(text, color = Color.White)
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
fun SecondaryButton(text: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp),
        contentPadding = PaddingValues(all = 16.dp),
        elevation = ButtonDefaults.elevation(defaultElevation = 2.dp),
    ) {
        Spacer(modifier = Modifier.weight(1.0f))
        Text(text, color = MaterialTheme.colors.primary)
        Spacer(modifier = Modifier.weight(1.0f))
    }
}