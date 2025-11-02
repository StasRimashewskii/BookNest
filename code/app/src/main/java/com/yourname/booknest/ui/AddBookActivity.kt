package com.example.booknest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.booknest.R
import com.example.booknest.data.Book
import com.example.booknest.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_add_book.*
import android.widget.Toast

class AddBookActivity : AppCompatActivity() {

    private lateinit var viewModel: BookViewModel
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        userId = intent.getIntExtra("USER_ID", 0)
        viewModel = ViewModelProvider(this)[BookViewModel::class.java]

        btn_search_api.setOnClickListener {
            val isbn = et_isbn.text.toString()
            if (isbn.isNotEmpty()) {
                viewModel.searchFromApi("isbn:$isbn", "YOUR_API_KEY_HERE")  // Вставьте ключ
            } else {
                Toast.makeText(this, "Enter ISBN", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.apiBooks.observe(this) { books ->
            if (books.isNotEmpty()) {
                val book = books[0]
                et_title.setText(book.title)
                et_author.setText(book.author)
                et_year.setText(book.year)
                et_genre.setText(book.genre ?: "")
            }
        }

        viewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }

        btn_save.setOnClickListener {
            val book = Book(
                title = et_title.text.toString(),
                author = et_author.text.toString(),
                year = et_year.text.toString(),
                isbn = et_isbn.text.toString(),
                genre = et_genre.text.toString(),
                userId = userId
            )
            viewModel.addBook(book)
            finish()
        }
    }
}