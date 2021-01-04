package com.kat4x.myebookreader.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kat4x.myebookreader.db.EntityBook

@Dao
interface BooksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: EntityBook)

    @Delete
    fun deleteBook(book: EntityBook)

    @Query("SELECT * FROM entitybook")
    fun getAllBooks(): List<EntityBook>

    @Update
    fun updateBook(book: EntityBook)
}