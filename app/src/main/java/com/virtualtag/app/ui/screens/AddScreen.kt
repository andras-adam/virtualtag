package com.virtualtag.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.virtualtag.app.data.ScanningViewModel
import com.virtualtag.app.ui.components.CardContainer
import com.virtualtag.app.ui.components.PrimaryButton
import com.virtualtag.app.ui.components.SecondaryButton
import com.virtualtag.app.viewmodels.CardViewModel

@Composable
fun AddScreen(
    model: CardViewModel,
    scanningViewModel: ScanningViewModel,
    goHome: () -> Unit,
    goBack: () -> Unit
) {
    val scannedTag = scanningViewModel.scannedTag.observeAsState(null)

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
                    CardContainer(onClick = { }, enabled = false) {
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
                    // TODO - add name and color selection
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
                        PrimaryButton(text = "Ok", onClick = {/* TODO - submit to ViewModel */ })
                    }
                }
            }
        }
    }
}
