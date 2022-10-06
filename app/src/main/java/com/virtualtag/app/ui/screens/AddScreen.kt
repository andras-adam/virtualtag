package com.virtualtag.app.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.virtualtag.app.data.ScanningViewModel
import com.virtualtag.app.data.toHex
import com.virtualtag.app.db.Card
import com.virtualtag.app.ui.components.CardContainer
import com.virtualtag.app.ui.components.ColorButton
import com.virtualtag.app.ui.components.PrimaryButton
import com.virtualtag.app.ui.components.SecondaryButton
import com.virtualtag.app.ui.theme.WhiteBG
import com.virtualtag.app.ui.theme.cardBackGroundColors
import com.virtualtag.app.utils.colorToString
import com.virtualtag.app.viewmodels.CardViewModel

@Composable
fun AddScreen(
    model: CardViewModel,
    scanningViewModel: ScanningViewModel,
    goHome: () -> Unit,
    goBack: () -> Unit
) {
    val context = LocalContext.current
    val scannedTag = scanningViewModel.scannedTag.observeAsState()
    val tagId = scannedTag.value?.id?.toHex() ?: "0"

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var color by remember { mutableStateOf("#fff8f8f8") }

    fun addCardToDb() {
        model.addCard(
            Card(
                id = tagId,
                name = name.text,
                color = color
            )
        )
        Toast.makeText(context, "Card added successfully!", Toast.LENGTH_SHORT).show()
        goHome()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add new card") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            null
                        )
                    }
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(8.dp), color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    CardContainer(onClick = { }, enabled = false, color = WhiteBG) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 12.dp,
                                    bottom = 12.dp,
                                    start = 4.dp,
                                    end = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.CheckCircle,
                                null, modifier = Modifier
                                    .size(48.dp)
                                    .padding(end = 16.dp)
                            )
                            Text("NFC tag scanned successfully", modifier = Modifier)
                        }
                    }

                    TextField(
                        value = name,
                        onValueChange = { newName ->
                            name = newName
                        },
                        label = { Text(text = "Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        )
                    )
                    ColorButton(colors = cardBackGroundColors, onColorSelected = {value ->
                        color = colorToString(value)
                    })
                }
                Row {
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                    ) {
                        SecondaryButton(text = "Cancel", onClick = goHome)
                    }
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(start = 4.dp)
                    ) {
                        PrimaryButton(text = "Ok", onClick = {
                            if (name.text == "") {
                                return@PrimaryButton Toast.makeText(
                                    context,
                                    "Please set a name for your card",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            addCardToDb()
                        })
                    }
                }
            }
        }
    }
}
