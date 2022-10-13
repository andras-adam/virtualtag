package com.virtualtag.app.ui.components

import android.nfc.tech.MifareUltralight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.virtualtag.app.db.Card
import com.virtualtag.app.utils.formatHex
import com.virtualtag.app.R

@Composable
fun MifareUltralightView(card: Card) {
    val serialNumber = formatHex(card.serialNumber)
    val techList = card.techList
        .replace("android.nfc.tech.", "")
        .replace(",", ", ")
    val type = when (card.mifareUltralightType ?: MifareUltralight.TYPE_UNKNOWN) {
        MifareUltralight.TYPE_ULTRALIGHT -> "MifareUltralight Ultralight"
        MifareUltralight.TYPE_ULTRALIGHT_C -> "MifareUltralight Ultralight C"
        else -> "MifareUltralight ${stringResource(R.string.unknown)}"
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
    val dataPageCount = data.length / 2 / MifareUltralight.PAGE_SIZE

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            DataRow(
                title = stringResource(R.string.serial_number),
                content = serialNumber,
                icon = Icons.Filled.Key)
        }
        item {
            DataRow(
                title = stringResource(R.string.technologies),
                content = techList,
                icon = Icons.Filled.Style)
        }
        item {
            DataRow(
                title = stringResource(R.string.type),
                content = type,
                icon = Icons.Filled.Category)
        }
        item {
            DataRow(
                title = stringResource(R.string.mem_size),
                content = "$size bytes",
                icon = Icons.Filled.Save)
        }
        item {
            DataRow(
                title = stringResource(R.string.transcieve_timeout),
                content = "$timeout ${stringResource(R.string.ms)}",
                icon = Icons.Filled.Sensors)
        }
        item {
            DataRow(
                title = stringResource(R.string.transcieve_max_length),
                content = "$maxTranscieveLength ${stringResource(R.string.bytes)}",
                icon = Icons.Filled.Sensors)
        }
        item {
            DataRow(
                title = stringResource(R.string.atqa),
                content = atqa,
                icon = Icons.Filled.Code)
        }
        item {
            DataRow(
                title = stringResource(R.string.sak),
                content = "$sak",
                icon = Icons.Filled.Code)
        }
        if (data.isNotEmpty()) {
            items(dataPageCount) { index ->
                DataRow(
                    title = "${stringResource(R.string.mem_page)} ${index + 1}",
                    content = formatHex(data.substring(index * 8, index * 8 + 8)),
                    icon = Icons.Filled.Memory)
            }
        }
    }
}
