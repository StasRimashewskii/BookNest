package com.example.booknest.repository

import com.example.booknest.api.ApiClient
import com.example.booknest.data.Book
import com.example.booknest.db.BookDao
import com.example.booknest.db.UserDao
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao, private val userDao: UserDao) {

    fun getAllBooks(userId: Int): Flow<List<Book>> = bookDao.getAllBooks(userId)

    suspend fun insertBook(book: Book) = bookDao.insert(book)

    suspend fun updateBook(book: Book) = bookDao.update(book)

    suspend fun deleteBook(book: Book) = bookDao.delete(book)

    fun searchBooks(userId: Int, query: String): Flow<List<Book>> = bookDao.searchBooks(userId, query)

    fun filterByStatus(userId: Int, status: String): Flow<List<Book>> = bookDao.filterByStatus(userId, status)

    suspend fun searchFromApi(query: String, apiKey: String): List<Book> {
        val response = ApiClient.api.searchBooks(query, apiKey)
        return response.items?.map { item ->
            val volume = item.volumeInfo
            Book(
                title = volume.title,
                author = volume.authors?.joinToString() ?: "",
                year = volume.publishedDate ?: "",
                isbn = volume.industryIdentifiers?.find { it.type == "ISBN_13" }?.identifier,
                genre = volume.categories?.joinToString(),
                coverUrl = volume.imageLinks?.thumbnail,
                status = "Want to Read",  // Default
                userId = -1  // Будет установлен позже
            )
        } ?: emptyList()
    }

    suspend fun registerUser(username: String, password: String): Boolean {
        if (userDao.getUser(username, password) != null) return false  // Уже существует
        userDao.insert(User(username = username, password = password))
        return true
    }

    suspend fun loginUser(username: String, password: String): User? {
        return userDao.getUser(username, password)
    }
}