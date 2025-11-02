package com.example.booknest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booknest.R
import com.example.booknest.data.Book
import kotlinx.android.synthetic.main.item_book.view.*

class BookAdapter(private val onClick: (Book) -> Unit) : ListAdapter<Book, BookAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view) {
        fun bind(book: Book, onClick: (Book) -> Unit) {
            itemView.tv_title.text = book.title
            itemView.tv_author.text = book.author
            itemView.tv_status.text = book.status
            Glide.with(itemView).load(book.coverUrl).into(itemView.iv_cover)
            itemView.setOnClickListener { onClick(book) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false) as ViewGroup
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    object DiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean = oldItem == newItem
    }
}