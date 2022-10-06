package com.virtualtag.app.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Nfc
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.virtualtag.app.data.ScanningViewModel
import com.virtualtag.app.ui.components.CardContainer
import com.virtualtag.app.ui.components.PrimaryButton

@Composable
fun ScanScreen(scanningViewModel: ScanningViewModel, goBack: () -> Unit, addCard: () -> Unit) {
    // Enable scanning while the screen is active
    val activity = LocalContext.current as Activity
    DisposableEffect(LocalLifecycleOwner.current) {
        scanningViewModel.enableScanning(activity)
        onDispose {
            scanningViewModel.disableScanning(activity)
        }
    }

    // Observe scanned tag
    val scannedTag = scanningViewModel.scannedTag.observeAsState(null)

    // If a tag was detected, go to the next screen
    LaunchedEffect(scannedTag.value) {
        if (scannedTag.value != null) addCard()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scan") },
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
                .padding(8.dp),
            color = MaterialTheme.colors.background,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.weight(1F)) {
                    CardContainer(onClick = { }, enabled = false) {
                        Spacer(Modifier.weight(1f))
                        Text(
                            "Ready to scan",
                            color = Color.Black,
                            style = MaterialTheme.typography.h1,
                        )
                        Spacer(Modifier.weight(2f))
                        Icon(
                            Icons.Outlined.Nfc,
                            null, modifier = Modifier.size(172.dp),
                            tint = MaterialTheme.colors.primaryVariant
                        )
                        Spacer(Modifier.weight(2f))
                        Text("Hold your device near your NFC tag to scan")
                        Spacer(Modifier.weight(2f))
                    }
                }
                PrimaryButton(text = "Cancel", onClick = goBack)
            }
        }
    }
}
