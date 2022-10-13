package com.virtualtag.app.ui.screens

import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.virtualtag.app.ui.components.MifareClassicView
import com.virtualtag.app.ui.components.MifareUltralightView
import com.virtualtag.app.viewmodels.CardViewModel

@Composable
fun CardScreen(
    model: CardViewModel,
    id: String,
    editCard: (id: String) -> Unit,
    goBack: () -> Unit
) {
    val card = model.getCardById(id).observeAsState(null)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(card.value?.name ?: "") },
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
                if (card.value == null) {
                    // Loading
                } else if (card.value?.techList?.contains(MifareClassic::class.java.name) == true) {
                    MifareClassicView(card = card.value!!)
                } else if (card.value?.techList?.contains(MifareUltralight::class.java.name) == true) {
                    MifareUltralightView(card = card.value!!)
                }
            }
        }
    }
}
