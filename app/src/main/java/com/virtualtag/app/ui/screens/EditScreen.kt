package com.virtualtag.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.virtualtag.app.viewmodels.CardViewModel
import com.virtualtag.app.R
import com.virtualtag.app.db.Card
import com.virtualtag.app.ui.components.ColorButton
import com.virtualtag.app.ui.components.PrimaryButton
import com.virtualtag.app.ui.components.SecondaryButton
import com.virtualtag.app.ui.theme.cardBackGroundColors
import com.virtualtag.app.utils.colorToString
import com.virtualtag.app.utils.stringToColor

@Composable
fun EditScreen(model: CardViewModel, id: String, goBack: () -> Unit, goHome: () -> Unit) {
    val context = LocalContext.current
    val card =
        model.getCardById(id)
            .observeAsState(
                Card(
                    id = "0",
                    name = "",
                    color = "#fff8f8f8",
                    techList = ""
                )
            )

    var name by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(colorToString(cardBackGroundColors[0])) }

    LaunchedEffect(key1 = card.value) {
        if (card.value.id == "0") return@LaunchedEffect
        name = card.value.name
        color = card.value.color
    }

    fun editCardInDb() {
        model.updateCard(Card(id = card.value.id, name = name, color = color, techList = ""))
        Toast.makeText(
            context,
            context.getString(R.string.card_updated_success),
            Toast.LENGTH_SHORT
        )
            .show()
        goHome()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.edit)) },
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
                .fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    TextField(
                        value = name,
                        onValueChange = { newName ->
                            name = newName
                        },
                        label = { Text(stringResource(R.string.name)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = MaterialTheme.colors.secondaryVariant
                        )
                    )
                    ColorButton(colors = cardBackGroundColors, onColorSelected = { value ->
                        color = colorToString(value)
                    }, selected = stringToColor(color))
                }
                Row {
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                    ) {
                        SecondaryButton(
                            text = stringResource(R.string.cancel),
                            onClick = goBack,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                        )
                    }
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(start = 4.dp)
                    ) {
                        PrimaryButton(text = "Ok", onClick = {
                            if (name == "") {
                                return@PrimaryButton Toast.makeText(
                                    context,
                                    context.getString(R.string.empty_name_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            editCardInDb()
                        }, modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
                    }
                }
            }
        }
    }
}
