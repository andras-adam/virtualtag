package com.virtualtag.app.ui.components

import android.nfc.tech.MifareClassic
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.virtualtag.app.db.Card
import com.virtualtag.app.utils.formatHex

@Composable
fun MifareClassicView(card: Card) {
    val id = formatHex(card.id)
    val techList = card.techList
        .replace("android.nfc.tech.", "")
        .replace(",", ", ")
    val type = when (card.mifareClassicType ?: MifareClassic.TYPE_UNKNOWN) {
        MifareClassic.TYPE_CLASSIC -> "Classic"
        MifareClassic.TYPE_PLUS -> "Plus"
        MifareClassic.TYPE_PRO -> "Pro"
        else -> "Unknown"
    }
    val sectorCount = card.mifareClassicSectorCount ?: 0
    val blockCount = card.mifareClassicBlockCount ?: 0
    val size = (card.mifareClassicSize ?: 0) * MifareClassic.BLOCK_SIZE
    val timeout = card.mifareClassicTimeout ?: 0
    val maxTranscieveLength = card.mifareClassicMaxTransceiveLength ?: 0
    val atqa = card.mifareClassicAtqa ?: ""
    val sak = card.mifareClassicSak ?: 0
    val data = card.mifareClassicData ?: ""

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            DataRow(title = "Serial number", content = id, icon = Icons.Filled.Key)
        }
        item {
            DataRow(title = "NFC technologies", content = techList, icon = Icons.Filled.Style)
        }
        item {
            DataRow(title = "MifareClassic type", content = type, icon = Icons.Filled.Category)
        }
        item {
            DataRow(title = "Memory size", content = "$size bytes", icon = Icons.Filled.Save)
        }
        item {
            DataRow(title = "Memory sector count", content = "$sectorCount", icon = Icons.Filled.Save)
        }
        item {
            DataRow(title = "Memory block count", content = "$blockCount", icon = Icons.Filled.Save)
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
        if (data.isNotEmpty()) {
            items(blockCount) { index ->
                DataRow(
                    title = "Memory block ${index + 1}",
                    content = formatHex(data.substring(index * 32, index * 32 + 32)),
                    icon = Icons.Filled.Memory
                )
            }
        }
    }
}
