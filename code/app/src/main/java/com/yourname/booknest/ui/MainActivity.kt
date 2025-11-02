package com.example.booknest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booknest.data.Book
import com.example.booknest.ui.AddBookActivity
import com.example.booknest.ui.BookInfoActivity
import com.example.booknest.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.booknest.ui.SignInActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BookViewModel
    private lateinit var adapter: BookAdapter
    private var userId: Int = 0
    private var isGuest: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userId = intent.getIntExtra("USER_ID", 0)
        isGuest = intent.getBooleanExtra("IS_GUEST", true)

        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
        viewModel.loadBooks(userId)

        adapter = BookAdapter { book ->
            startActivity(Intent(this, BookInfoActivity::class.java).apply {
                putExtra("BOOK", book)
                putExtra("USER_ID", userId)
            })
        }
        rv_books.layoutManager = LinearLayoutManager(this)
        rv_books.adapter = adapter

        viewModel.books.observe(this) { books ->
            adapter.submitList(books)
        }

        btn_add.setOnClickListener {
            if (isGuest) {
                Toast.makeText(this, "Guests cannot add books", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, AddBookActivity::class.java).apply {
                    putExtra("USER_ID", userId)
                })
            }
        }

        et_search.setOnEditorActionListener { v, actionId, event ->
            viewModel.searchBooks(userId, et_search.text.toString())
            true
        }

        val statuses = arrayOf("All", "Reading", "Read", "Want to Read")
        spinner_status.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statuses)
        spinner_status.setOnItemSelectedListener { parent, view, position, id ->
            val status = statuses[position]
            if (status == "All") viewModel.loadBooks(userId) else viewModel.filterByStatus(userId, status)
        }

        btn_logout.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        if (isGuest) {
            // Добавить демо-книги, если нужно
        }
    }
}