package com.kat4x.myebookreader.db

import android.net.Uri
import androidx.core.net.toUri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kat4x.myebookreader.BooksType

@Entity
data class EntityBook(
    @PrimaryKey
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
