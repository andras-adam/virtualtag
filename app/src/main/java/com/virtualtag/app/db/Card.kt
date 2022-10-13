package com.virtualtag.app.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    // Display properties
    val name: String,
    val color: String,

    // Tag properties
    val serialNumber: String,
    val techList: String,

    // MifareClassic properties
    val mifareClassicAtqa: String? = null,
    val mifareClassicSak: Int? = null,
    val mifareClassicTimeout: Int? = null,
    val mifareClassicMaxTransceiveLength: Int? = null,
    val mifareClassicSize: Int? = null,
    val mifareClassicType: Int? = null,
    val mifareClassicSectorCount: Int? = null,
    val mifareClassicBlockCount: Int? = null,
    val mifareClassicData: String? = null,

    // MifareUltralight properties
    val mifareUltralightAtqa: String? = null,
    val mifareUltralightSak: Int? = null,
    val mifareUltralightTimeout: Int? = null,
    val mifareUltralightMaxTransceiveLength: Int? = null,
    val mifareUltralightType: Int? = null,
    val mifareUltralightData: String? = null,
)
