package com.virtualtag.app.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CardDao {
    @Query("SELECT * FROM card")
    fun getAllCards(): LiveData<List<Card>>

    @Query("SELECT * FROM card WHERE card.id = :id")
    fun getCardById(id: Int): LiveData<Card>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCard(card: Card)

    @Query("UPDATE card SET name = :name, color = :color WHERE card.id = :id")
    fun updateCard(id: Int, name: String, color: String)

    @Delete
    fun deleteCard(card: Card)
}
