package com.kat4x.myebookreader.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kat4x.myebookreader.R
import com.kat4x.myebookreader.databinding.ItemDiscoveryBookBinding
import com.kat4x.myebookreader.model.rv.Book
import com.kat4x.myebookreader.utils.px
import com.kat4x.myebookreader.utils.toPx

class RecentlyBooksAdapter : RecyclerView.Adapter<RecentlyBooksAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: ItemDiscoveryBookBinding) :
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
            ItemDiscoveryBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = differ.currentList[position]
        holder.itemView.apply {
            val binding = ItemDiscoveryBookBinding.bind(this)
            if (!book.bookCover.isNullOrEmpty())
                Glide.with(context).load("${book.bookCover}")
                    .into(binding.ivBook)
            binding.run {
                tvChapterCount.text = "${context.getString(R.string.chapter)} ${book.id}"
                booksName.text = book.booksName
            }

            if (position == 0) {
                val params =
                    LinearLayout.LayoutParams(170.toPx(), ViewGroup.LayoutParams.WRAP_CONTENT)
                params.setMargins(8.toPx(), 8.px, 4.toPx(), 16.toPx())
                binding.card.layoutParams = params

//                Toast.makeText(
//                    context,
//                    "First element margins: End:${binding.card.marginEnd}," +
//                            " Start:${binding.card.marginStart}," +
//                            " Bottom:${binding.card.marginBottom}, Top:${binding.card.marginTop}",
//                    Toast.LENGTH_LONG
//                ).show()
            } else if (position == differ.currentList.lastIndex) {
                val params =
                    LinearLayout.LayoutParams(170.toPx(), ViewGroup.LayoutParams.WRAP_CONTENT)
                params.setMargins(4.toPx(), 8.px, 8.toPx(), 16.toPx())
                binding.card.layoutParams = params
//                Toast.makeText(
//                    context,
//                    "First element margins: End:${binding.card.marginEnd}," +
//                            " Start:${binding.card.marginStart}," +
//                            " Bottom:${binding.card.marginBottom}, Top:${binding.card.marginTop}",
//                    Toast.LENGTH_LONG
//                ).show()
            } else {
                val params =
                    LinearLayout.LayoutParams(170.toPx(), ViewGroup.LayoutParams.WRAP_CONTENT)
                params.setMargins(4.toPx(), 8.px, 4.toPx(), 16.toPx())
                binding.card.layoutParams = params
            }


            binding.card.setOnClickListener {
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
