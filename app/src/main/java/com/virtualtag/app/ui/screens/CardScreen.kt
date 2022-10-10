package com.virtualtag.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.virtualtag.app.db.Card
import com.virtualtag.app.viewmodels.CardViewModel

@Composable
fun CardScreen(
    model: CardViewModel,
    id: String,
    editCard: (id: String) -> Unit,
    goBack: () -> Unit
) {
    val card =
        model.getCardById(id)
            .observeAsState(Card(id = "0", name = "Unknown card", color = "#fff8f8f8"))
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(card.value.name) },
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
                .padding(8.dp), color = colors.background
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Card details - placeholder")
            }
        }
    }
}
