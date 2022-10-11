package com.virtualtag.app.ui.screens

import android.nfc.NfcAdapter
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.virtualtag.app.ui.theme.BlackBG
import com.virtualtag.app.utils.stringToColor
import com.virtualtag.app.viewmodels.CardViewModel
import com.virtualtag.app.R
import com.virtualtag.app.ui.components.*

@Composable
fun HomeScreen(model: CardViewModel, viewCard: (id: String) -> Unit, scanCard: () -> Unit) {
    val context = LocalContext.current
    val cardList = model.getAllCards().observeAsState(listOf())
    var errorDialogOpen by remember { mutableStateOf(false) }

    // Set up NFC adapter
    val adapter = NfcAdapter.getDefaultAdapter(context)

    // Check if the phone has NFC sensor and show error if sensor is not available
    fun onScanClick() {
        if (adapter != null) return scanCard()
        errorDialogOpen = true
    }

    Scaffold {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            color = MaterialTheme.colors.secondaryVariant,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
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
                                color = BlackBG,
                                modifier = Modifier.padding(top = 48.dp, bottom = 48.dp)
                            )
                        }
                    }
                }
                PrimaryButton(
                    text = stringResource(R.string.scan_new_card),
                    onClick = { onScanClick() })
            }
        }
    }

    if (errorDialogOpen) {
        NfcErrorDialog(
            closeDialog = { errorDialogOpen = false },
        )
    }
}
