package com.virtualtag.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.virtualtag.app.viewmodels.CardViewModel

@Composable
fun HomeScreen(model: CardViewModel, viewCard: (id: Int) -> Unit, scanCard: () -> Unit) {
  Scaffold(
    topBar = { TopAppBar(title = { Text("Home") }) }
  ) {
    Surface(modifier = Modifier
      .padding(it)
      .fillMaxSize()) {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text("List cards here")
        Button(onClick = { viewCard(3) }) {
          Text("View card 3")
        }
        Button(onClick = scanCard) {
          Text("Scan card")
        }
      }
    }
  }
}
