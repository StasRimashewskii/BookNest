package com.example.booknest.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "booknest_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}