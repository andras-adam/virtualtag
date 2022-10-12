package com.virtualtag.app.data

import android.nfc.tech.MifareClassic
import android.util.Log
import java.io.IOException

class MifareClassicHelper(tag: MifareClassic) {
    val timeout: Int
    val maxTransceiveLength: Int
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
