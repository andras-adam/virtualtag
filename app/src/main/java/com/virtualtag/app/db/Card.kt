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
    // TODO - figure out what kind of card data to save (Tag cannot be saved in Room)
    // val data: Tag,
    val name: String,
    val color: String,

)