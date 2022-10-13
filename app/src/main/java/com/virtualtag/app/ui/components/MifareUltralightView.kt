package com.virtualtag.app.ui.components

import android.nfc.tech.MifareUltralight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.virtualtag.app.db.Card
import com.virtualtag.app.utils.formatHex

@Composable
fun MifareUltralightView(card: Card) {
    val id = formatHex(card.id)
    val techList = card.techList
        .replace("android.nfc.tech.", "")
        .replace(",", ", ")
    val type = when (card.mifareUltralightType ?: MifareUltralight.TYPE_UNKNOWN) {
        MifareUltralight.TYPE_ULTRALIGHT -> "Ultralight"
        MifareUltralight.TYPE_ULTRALIGHT_C -> "Ultralight C"
        else -> "Unknown"
    }
    val size = when (card.mifareUltralightType ?: MifareUltralight.TYPE_UNKNOWN) {
        MifareUltralight.TYPE_ULTRALIGHT -> 16 * MifareUltralight.PAGE_SIZE
        MifareUltralight.TYPE_ULTRALIGHT_C -> 48 * MifareUltralight.PAGE_SIZE
        else -> 0
    }
    val timeout = card.mifareUltralightTimeout ?: 0
    val maxTranscieveLength = card.mifareUltralightMaxTransceiveLength ?: 0
    val atqa = card.mifareUltralightAtqa ?: ""
    val sak = card.mifareUltralightSak ?: 0
    val data = card.mifareUltralightData ?: ""

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            DataRow(title = "Serial number", content = id, icon = Icons.Filled.Key)
        }
        item {
            DataRow(title = "NFC technologies", content = techList, icon = Icons.Filled.Style)
        }
        item {
            DataRow(title = "MifareUltralight type", content = type, icon = Icons.Filled.Category)
        }
        item {
            DataRow(title = "Memory size", content = "$size bytes", icon = Icons.Filled.Save)
        }
        item {
            DataRow(title = "Transcieve timeout", content = "$timeout ms", icon = Icons.Filled.Sensors)
        }
        item {
            DataRow(title = "Transcieve max length", content = "$maxTranscieveLength bytes", icon = Icons.Filled.Sensors)
        }
        item {
            DataRow(title = "ATQA", content = atqa, icon = Icons.Filled.Code)
        }
        item {
            DataRow(title = "SAK", content = "$sak", icon = Icons.Filled.Code)
        }
//        if (data.isNotEmpty()) {
//            items(blockCount) { index ->
//                DataRow(
//                    title = "Memory block ${index + 1}",
//                    content = formatHex(data.substring(index * 32, index * 32 + 32)),
//                    icon = Icons.Filled.Memory
//                )
//            }
//        }
    }
}
