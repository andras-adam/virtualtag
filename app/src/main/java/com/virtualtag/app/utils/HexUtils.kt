package com.virtualtag.app.utils

// Byte array to HEX helper
fun ByteArray.toHex(): String = joinToString(separator = "") { "%02x".format(it) }

// Format HEX strings into colon (:) delimited HEX strings
// E.g.: BF55 -> BF:55
fun formatHex(content: String): String {
    val result = arrayListOf<String>()
    for (i in content.indices step 2) {
        result.add(content.substring(i, i + 2))
    }
    return result.joinToString(":")
}
