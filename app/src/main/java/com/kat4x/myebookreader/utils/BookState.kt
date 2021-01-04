package com.kat4x.myebookreader.utils

import com.kat4x.myebookreader.model.rv.Book

sealed class BookState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : BookState<T>(data)
    class Error<T>(message: String, data: T? = null) : BookState<T>(data, message)
    class Loading<T> : BookState<T>()
    class Empty<T> : BookState<T>()
}