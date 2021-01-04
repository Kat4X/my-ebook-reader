package com.kat4x.myebookreader.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(EntityBook::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun booksDao(): BooksDao
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//        fun getInstance(context: Context): AppDatabase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
//            }
//        private fun buildDatabase(context: Context) =
//            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "EbookDB_db").build()
//    }
}