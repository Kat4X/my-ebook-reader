package com.kat4x.myebookreader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kat4x.myebookreader.db.EntityBook
import com.kat4x.myebookreader.model.rv.Book
import com.kat4x.myebookreader.utils.BookState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    val repo: RepositoryBook
) : ViewModel() {

    private val _books = MutableStateFlow<BookState<MutableList<Book>>>(BookState.Empty())
    val books: StateFlow<BookState<MutableList<Book>>> = _books

    private val _pageState = MutableStateFlow<Int>(0)
    val pageState: StateFlow<Int> get() = _pageState

    private val _pageOffset = MutableStateFlow<Float>(0f)
    val pageOffset: StateFlow<Float> get() = _pageOffset

    private fun convertEntityToSimpleModel(flow: List<EntityBook>): BookState<MutableList<Book>> {
        try {
            val list: MutableList<Book> = mutableListOf()
            flow.forEach {
                val book = Book(
                    it.id,
                    it.type,
                    it.booksUri,
                    it.booksName,
//                    it.booksAuthor,
                    it.bookCover
//                    it.chapter,
//                    it.timeLeft,
//                    it.completed
                )
                list.add(book)
            }
            return BookState.Success(list)
        } catch (e: Exception) {
            Timber.e("$e")
            return BookState.Error("$e")
        }
    }

    private fun convertSimpleModelToEntity(flow: Book): EntityBook? {
        var entity: EntityBook
        return try {
            flow.let {
                entity = EntityBook(
                    it.id,
                    it.type,
                    it.booksUri,
                    it.booksName,
//                    it.booksAuthor,
                    it.bookCover
//                    it.chapter,
//                    it.timeLeft,
//                    it.completed
                )
            }
            entity
        } catch (e: Exception) {
            Timber.e("$e")
            null
        }
    }

    fun insetBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        val entity = convertSimpleModelToEntity(book)
        if (entity != null) {
            repo.insertBook(entity)
        }
    }

    fun setCurrentPage(page: Int) = viewModelScope.launch {
        _pageState.value = page
//        currentPage.postValue(page)
    }

    fun getRecentBooks() = viewModelScope.launch(Dispatchers.IO) {
        _books.value = convertEntityToSimpleModel(repo.getAllBooks())
        Timber.d("${repo.getAllBooks()}")
    }

    init {
        getRecentBooks()
    }

}