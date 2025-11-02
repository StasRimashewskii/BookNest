package com.example.booknest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.booknest.R
import com.example.booknest.data.Book
import com.example.booknest.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_book_info.*
import android.widget.ArrayAdapter

class BookInfoActivity : AppCompatActivity() {

    private lateinit var viewModel: BookViewModel
    private lateinit var book: Book
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_info)

        book = intent.getParcelableExtra("BOOK") ?: return
        userId = intent.getIntExtra("USER_ID", 0)
        viewModel = ViewModelProvider(this)[BookViewModel::class.java]

        tv_title.text = book.title
        tv_author.text = book.author
        tv_year.text = book.year
        tv_genre.text = book.genre
        et_comment.setText(book.comment)
        Glide.with(this).load(book.coverUrl).into(iv_cover)

        val statuses = arrayOf("Reading", "Read", "Want to Read")
        spinner_status.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statuses)
        spinner_status.setSelection(statuses.indexOf(book.status))

        btn_edit.setOnClickListener {
            val updatedBook = book.copy(
                comment = et_comment.text.toString(),
                status = spinner_status.selectedItem.toString()
            )
            viewModel.updateBook(updatedBook)
            finish()
        }

        btn_delete.setOnClickListener {
            viewModel.deleteBook(book)
            finish()
        }
    }
}