package com.virtualtag.app.data

import android.nfc.tech.MifareUltralight
import android.util.Log
import java.io.IOException

class MifareUltralightInfo(tag: MifareUltralight) {
    val type: Int
    val timeout: Int
    val maxTransceiveLength: Int
    val data: String

    init {
        type = tag.type
        timeout = tag.timeout
        maxTransceiveLength = tag.maxTransceiveLength
        data = readData(tag)
    }

    private fun readData(tag: MifareUltralight): String {
        try {
            var dataAccumulator = ""
            // Connect to the tag
            tag.connect()
            // Get the number of readable pages
            val readablePagesCount = when (type) {
                // Ultralight has 16 pages, all readable
                MifareUltralight.TYPE_ULTRALIGHT -> 16
                // Ultralight C has 48 pages, but the last 4 are unreadable
                MifareUltralight.TYPE_ULTRALIGHT_C -> 44
                else -> 0
            }
            // Read every fourth page, as 4 pages are read at a time by `readPages()`
            for (currentPage in 0..readablePagesCount) {
                if (currentPage % 4 == 0) dataAccumulator += tag.readPages(currentPage).toHex()
            }
            return dataAccumulator
        } catch (e: IOException) {
            e.localizedMessage?.let { Log.e("MifareUltralightHelper", it) }
            return ""
        }
    }
}
