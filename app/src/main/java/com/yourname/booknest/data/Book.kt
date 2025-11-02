package com.example.booknest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val year: String,
    val isbn: String? = null,
    val genre: String? = null,
    val comment: String? = null,
    val status: String = "Want to Read",  // По умолчанию
    val coverUrl: String? = null,  // URL обложки из API
    val userId: Int  // Связь с пользователем
)