package com.example.fetchhiringtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fetchhiringtask.repository.ItemRepository
import kotlinx.coroutines.Dispatchers

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {
    val items = liveData(Dispatchers.IO) {
        emit(repository.fetchItems())
    }
}
