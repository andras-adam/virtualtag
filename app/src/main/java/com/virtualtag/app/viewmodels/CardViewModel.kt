package com.virtualtag.app.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.virtualtag.app.db.Card
import com.virtualtag.app.db.CardDB
import kotlinx.coroutines.launch

class CardViewModel(application: Application) : ViewModel() {
    private val cardDB = CardDB.get(application)

    fun getAllCards(): LiveData<List<Card>> = cardDB.cardDao().getAllCards()

    fun getCardById(id: String): LiveData<Card> = cardDB.cardDao().getCardById(id)

    fun addCard(card: Card) {
        viewModelScope.launch {
            cardDB.cardDao().addCard(card)
        }
    }

    fun updateCard(card: Card) {
        viewModelScope.launch {
            cardDB.cardDao().updateCard(card)
        }
    }

    fun deleteCard(card: Card) {
        viewModelScope.launch {
            cardDB.cardDao().deleteCard(card)
        }
    }
}
