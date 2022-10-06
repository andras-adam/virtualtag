package com.virtualtag.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.virtualtag.app.ui.components.CardContainer
import com.virtualtag.app.ui.components.Logo
import com.virtualtag.app.ui.components.PrimaryButton
import com.virtualtag.app.utils.stringToColor
import com.virtualtag.app.viewmodels.CardViewModel

@Composable
fun HomeScreen(model: CardViewModel, viewCard: (id: String) -> Unit, scanCard: () -> Unit) {
    val cardList = model.getAllCards().observeAsState(listOf())
    Scaffold {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(8.dp), color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Logo(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))
                // List of all cards in the database
                LazyColumn {
                    items(cardList.value) { card ->
                        CardContainer(
                            onClick = { viewCard(card.id) },
                            enabled = true,
                            color = stringToColor(card.color)
                        ) {
                            Text(
                                card.name,
                                modifier = Modifier.padding(top = 48.dp, bottom = 48.dp)
                            )
                        }
                    }
                }
                PrimaryButton(text = "Scan new card", onClick = scanCard)
            }
        }
    }
}
