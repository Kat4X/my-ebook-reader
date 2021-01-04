package com.kat4x.myebookreader.fragments.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReadingViewModel: ViewModel() {

    private val _pageState = MutableStateFlow<Int>(0)
    val pageState: StateFlow<Int> get() = _pageState

    private val _pageOffset = MutableStateFlow<Float>(0f)
    val pageOffset: StateFlow<Float> get() = _pageOffset

//    val currentPage = MutableLiveData<Int>()

    fun setCurrentPage(page: Int) = viewModelScope.launch {
        _pageState.value = page
//        currentPage.postValue(page)
    }

    fun setCurrentOffset(offset: Float) = viewModelScope.launch {
        _pageOffset.value = offset
    }
}