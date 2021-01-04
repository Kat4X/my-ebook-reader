package com.kat4x.myebookreader.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kat4x.myebookreader.databinding.ItemPageBackgroundBinding
import com.kat4x.myebookreader.model.rv.Book

class PageBackgroundAdapter : RecyclerView.Adapter<PageBackgroundAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: ItemPageBackgroundBinding) :
        RecyclerView.ViewHolder(itemView.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(
            oldItem: Book,
            newItem: Book
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Book,
            newItem: Book
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPageBackgroundBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = differ.currentList[position]
        holder.itemView.apply {
            val binding = ItemPageBackgroundBinding.bind(this)
            if (!book.bookCover.isNullOrEmpty())
                Glide.with(context).load("${book.bookCover}")
                    .into(binding.color)


            setOnClickListener {
                onItemClickListener?.let { it(this, book) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((view: View, Book) -> Unit)? = null

    fun setOnItemClickListener(listener: (view: View, Book) -> Unit) {
        onItemClickListener = listener
    }
}
