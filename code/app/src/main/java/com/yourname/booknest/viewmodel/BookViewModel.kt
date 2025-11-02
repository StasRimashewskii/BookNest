package com.example.booknest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.booknest.data.Book
import com.example.booknest.db.DatabaseProvider
import com.example.booknest.repository.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookRepository
    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    private val _apiBooks = MutableLiveData<List<Book>>()
    val apiBooks: LiveData<List<Book>> get() = _apiBooks

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        val db = DatabaseProvider.getDatabase(application)
        repository = BookRepository(db.bookDao(), db.userDao())
    }

    fun loadBooks(userId: Int) {
        viewModelScope.launch {
            repository.getAllBooks(userId).collect { _books.value = it }
        }
    }

    fun addBook(book: Book) {
        viewModelScope.launch {
            repository.insertBook(book)
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

    fun searchBooks(userId: Int, query: String) {
        viewModelScope.launch {
            repository.searchBooks(userId, query).collect { _books.value = it }
        }
    }

    fun filterByStatus(userId: Int, status: String) {
        viewModelScope.launch {
            repository.filterByStatus(userId, status).collect { _books.value = it }
        }
    }

    fun searchFromApi(query: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val results = repository.searchFromApi(query, apiKey)
                _apiBooks.value = results
            } catch (e: Exception) {
                _error.value = "No internet or API error: ${e.message}"
            }
        }
    }

    suspend fun register(username: String, password: String): Boolean = repository.registerUser(username, password)

    suspend fun login(username: String, password: String): Int? = repository.loginUser(username, password)?.id
}