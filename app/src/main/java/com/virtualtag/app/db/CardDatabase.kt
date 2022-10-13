package com.virtualtag.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Card::class],
    version = 3
)
abstract class CardDB : RoomDatabase() {
    abstract fun cardDao(): CardDao

    companion object {
        private var sInstance: CardDB? = null

        // Initialize database instance
        @Synchronized
        fun get(context: Context): CardDB {
            if (sInstance == null) {
                sInstance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        CardDB::class.java, "cards.db"
                    ).fallbackToDestructiveMigration().build()
            }
            return sInstance!!
        }
    }
}