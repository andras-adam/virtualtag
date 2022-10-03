package com.virtualtag.app.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.virtualtag.app.data.ScanningViewModel
import com.virtualtag.app.data.toHex

@Composable
fun ScanScreen(scanningViewModel: ScanningViewModel, viewCard: (id: Int) -> Unit, goBack: () -> Unit) {
  // Enable scanning while the screen is active
  val activity = LocalContext.current as Activity
  DisposableEffect(LocalLifecycleOwner.current) {
    scanningViewModel.enableScanning(activity)
    onDispose {
      scanningViewModel.disableScanning(activity)
    }
  }

  // Observe scanned tag
  val scannedTag = scanningViewModel.scannedTag.observeAsState()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Scan") },
        navigationIcon = { IconButton(onClick = goBack) { Icon(Icons.Filled.ArrowBack, null) } }
      )
    }
  ) {
    Surface(modifier = Modifier
      .padding(it)
      .fillMaxSize()) {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text("Scan new cards here")
        if (scannedTag.value != null) {
          Text("ID: ${scannedTag.value?.id?.toHex()}")
          Text("Supports: ${scannedTag.value?.techList?.joinToString(", ")}")
        }
      }
    }
  }
}
