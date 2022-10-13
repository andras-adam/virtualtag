package com.virtualtag.app.data

import android.nfc.tech.MifareClassic
import android.nfc.tech.NfcA
import android.util.Log
import com.virtualtag.app.utils.toHex
import java.io.IOException

class MifareClassicInfo(tag: MifareClassic) {
    val timeout: Int
    val maxTransceiveLength: Int
    val atqa: String
    val sak: Int
    val size: Int
    val type: Int
    val sectorCount: Int
    val blockCount: Int
    val data: String

    init {
        timeout = tag.timeout
        maxTransceiveLength = tag.maxTransceiveLength
        size = tag.size
        type = tag.type
        sectorCount = tag.sectorCount
        blockCount = tag.blockCount
        // MifareClassic tags are also NfcA tags
        val nfca = NfcA.get(tag.tag)
        atqa = nfca.atqa.toHex()
        sak = nfca.sak.toInt()
        // Read memory data
        data = readData(tag)
    }

    private fun readData(tag: MifareClassic): String {
        try {
            var dataAccumulator = ""
            // Connect to the tag
            tag.connect()
            // Loop through sectors on the tag
            for (currentSector in 0 until tag.sectorCount) {
                // Authenticate sector with the default key
                val auth = tag.authenticateSectorWithKeyA(currentSector, MifareClassic.KEY_DEFAULT)
                if (!auth) throw IOException("Failed to authenticate sector")
                // Get block count in sector
                val sectorBlockCount = tag.getBlockCountInSector(currentSector)
                // Loop through blocks in the sector
                for (currentBlockInSector in 0 until sectorBlockCount) {
                    // Calculate the block's index
                    val blockIndex = tag.sectorToBlock(currentSector) + currentBlockInSector
                    // Read the block's data and store as HEX
                    dataAccumulator += tag.readBlock(blockIndex).toHex()
                }
            }
            return dataAccumulator
        } catch (e: IOException) {
            e.localizedMessage?.let { Log.e("MifareClassicHelper", it) }
            return ""
        }
    }
}
