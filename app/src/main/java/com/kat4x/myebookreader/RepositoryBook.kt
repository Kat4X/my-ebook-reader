package com.kat4x.myebookreader

import androidx.lifecycle.LiveData
import com.kat4x.myebookreader.db.BooksDao
import com.kat4x.myebookreader.db.EntityBook

class RepositoryBook constructor(
    private val booksDao: BooksDao
) {

    fun getAllBooks(): List<EntityBook> = booksDao.getAllBooks()

    suspend fun insertBook(book: EntityBook) = booksDao.insertBook(book)
    suspend fun deleteBook(book: EntityBook) = booksDao.deleteBook(book)
}