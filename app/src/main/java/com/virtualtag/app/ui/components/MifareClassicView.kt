package com.virtualtag.app.ui.components

import android.nfc.tech.MifareClassic
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
fun MifareClassicView(card: Card) {
    val serialNumber = formatHex(card.serialNumber)
    val techList = card.techList
        .replace("android.nfc.tech.", "")
        .replace(",", ", ")
    val type = when (card.mifareClassicType ?: MifareClassic.TYPE_UNKNOWN) {
        MifareClassic.TYPE_CLASSIC -> "MifareClassic Classic"
        MifareClassic.TYPE_PLUS -> "MifareClassic Plus"
        MifareClassic.TYPE_PRO -> "MifareClassic Pro"
        else -> "MifareClassic ${stringResource(R.string.unknown)}"
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
                title = stringResource(R.string.mem_sector_count),
                content = "$sectorCount",
                icon = Icons.Filled.Save)
        }
        item {
            DataRow(
                title = stringResource(R.string.mem_block_count),
                content = "$blockCount",
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
            items(blockCount) { index ->
                DataRow(
                    title = "${stringResource(R.string.mem_block)} ${index + 1}",
                    content = formatHex(data.substring(index * 32, index * 32 + 32)),
                    icon = Icons.Filled.Memory)
            }
        }
    }
}
