package com.virtualtag.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.virtualtag.app.ui.components.CardContainer
import com.virtualtag.app.ui.components.PrimaryButton
import com.virtualtag.app.viewmodels.CardViewModel

@Composable
fun HomeScreen(model: CardViewModel, viewCard: (id: Int) -> Unit, scanCard: () -> Unit) {
    Scaffold(
    ) {
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
                Row(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
                    Text(
                        "VIRTUAL",
                        color = Color.Black,
                        style = MaterialTheme.typography.h1,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        "TAG",
                        color = MaterialTheme.colors.primaryVariant,
                        style = MaterialTheme.typography.h1
                    )
                }
                // TODO - display card list with LazyColumn
                CardContainer(onClick = { viewCard(3) }) {
                    Text("Card 3", modifier = Modifier.padding(top = 48.dp, bottom = 48.dp))
                }
                PrimaryButton(text = "Scan new card", onClick = scanCard)
            }
        }
    }
}
