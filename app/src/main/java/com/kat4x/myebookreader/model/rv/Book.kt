package com.kat4x.myebookreader.model.rv

import android.net.Uri
import androidx.core.net.toUri
import com.kat4x.myebookreader.BooksType

data class Book(
    val id: Int = 0,
    val type: Int = 0,
    val booksUri: String = "",
    val booksName: String = "",
//    val booksAuthor: String = "",
    val bookCover: String? = ""
//    val chapter: Int = 0,
//    val timeLeft: Float? = 0f,
//    val completed: Boolean = false
)
