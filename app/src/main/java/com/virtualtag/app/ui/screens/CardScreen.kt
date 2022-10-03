package com.virtualtag.app.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.virtualtag.app.viewmodels.CardViewModel

@Composable
fun CardScreen(model: CardViewModel, id: Int, goBack: () -> Unit) {
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Card $id") },
        navigationIcon = { IconButton(onClick = goBack) { Icon(Icons.Filled.ArrowBack, null) } }
      )
    }
  ) {
    Surface(modifier = Modifier
      .padding(it)
      .fillMaxSize()) {
      Text("View card details here")
    }
  }
}
