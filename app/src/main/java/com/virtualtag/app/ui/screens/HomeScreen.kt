package com.virtualtag.app.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.virtualtag.app.viewmodels.CardViewModel

@Composable
fun HomeScreen(model: CardViewModel, viewCard: (id: Int) -> Unit) {
  Scaffold(
    topBar = { TopAppBar(title = { Text("Home") }) }
  ) {
    Surface(modifier = Modifier.padding(it).fillMaxSize()) {
      Text("List cards here")
    }
  }
}
