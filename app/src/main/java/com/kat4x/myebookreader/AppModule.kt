package com.kat4x.myebookreader

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kat4x.myebookreader.utils.Constants


//@Module
//@InstallIn(ApplicationComponent::class)
//class AppModule {
//
//    @Singleton
//    @Provides
//    fun provideBooksDatabase(
//        @ApplicationContext app: Context
//    ) = Room.databaseBuilder(app, AppDatabase::class.java, Constants.BOOKS_DATABASE_NAME).build()
//
//    @Singleton
//    @Provides
//    fun provideBooksDao(db: AppDatabase) = db.booksDao()
//}