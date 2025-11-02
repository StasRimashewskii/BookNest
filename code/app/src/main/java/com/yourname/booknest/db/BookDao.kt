package com.example.booknest.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.booknest.data.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert
    suspend fun insert(book: Book)

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("SELECT * FROM books WHERE userId = :userId")
    fun getAllBooks(userId: Int): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE userId = :userId AND (title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%')")
    fun searchBooks(userId: Int, query: String): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE userId = :userId AND status = :status")
    fun filterByStatus(userId: Int, status: String): Flow<List<Book>>
}