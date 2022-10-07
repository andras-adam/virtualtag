package com.virtualtag.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondaryVariant)
    ) {
        Spacer(modifier = Modifier.weight(1.0f))
        Text(text, color = MaterialTheme.colors.secondary)
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
fun ColorButton(colors: List<Color>, onColorSelected: (Color) -> Unit) {
    var colorPickerOpen by remember { mutableStateOf(false) }
    var currentlySelected by remember { mutableStateOf(colors[0]) }

    Card(
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10))
            .clickable {
                colorPickerOpen = true
            },
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Change card background color", color = MaterialTheme.colors.secondary
            )
            Canvas(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(10))
                    .background(currentlySelected)
                    .border(width = 1.dp, color = Color.Gray)
                    .clickable {
                        colorPickerOpen = true
                    }
            ) {}
        }
    }

    if (colorPickerOpen) {
        ColorPickerDialog(
            colorList = colors,
            onDismiss = { colorPickerOpen = false },
            currentlySelected = currentlySelected,
            onColorSelected = {
                currentlySelected = it
                onColorSelected(it)
            }
        )
    }
}