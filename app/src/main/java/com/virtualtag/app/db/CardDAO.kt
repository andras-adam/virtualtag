package com.virtualtag.app.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CardDao {
    @Query("SELECT * FROM card")
    fun getAllCards(): LiveData<List<Card>>

    @Query("SELECT * FROM card WHERE card.id = :id")
    fun getCardById(id: Int): LiveData<Card>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCard(card: Card)

    @Update
    fun updateCard(card: Card)

    @Delete
    fun deleteCard(card: Card)
}