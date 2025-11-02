package com.example.booknest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.booknest.data.Book
import com.example.booknest.data.User

@Database(entities = [Book::class, User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun userDao(): UserDao
}