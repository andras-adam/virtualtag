package com.virtualtag.app.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    /*
    * The tag ID from android.nfc.Tag should be the primary key, so in case the user tries to add
    * the same Tag twice, then OnConflictStrategy can easily handle the duplicates within the DAO
    * (note - the tag ID coming from android.nfc.Tag cannot be handled as Integer)
    */
    @PrimaryKey
    val id: String,
    val name: String,
    val color: String,
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
