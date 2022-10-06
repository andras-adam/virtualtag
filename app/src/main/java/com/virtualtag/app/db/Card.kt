package com.virtualtag.app.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    /*
    * Id should be added manually from the Tag data, so in case the user tries to add the same Tag
    * twice, then OnConflictStrategy can easily handle the duplicates within the DAO
    */
    @PrimaryKey
    val id: Int,
    // TODO - figure out what kind of card data to save (Tag cannot be saved in Room)
    // val data: Tag,
    val name: String,
    val color: String,

)