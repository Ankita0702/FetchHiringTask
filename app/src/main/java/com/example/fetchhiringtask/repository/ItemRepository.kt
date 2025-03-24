package com.example.fetchhiringtask.repository

import com.example.fetchhiringtask.model.Item
import com.example.fetchhiringtask.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemRepository(private val apiService: ApiService) {
    suspend fun fetchItems(): List<Item> {
        return withContext(Dispatchers.IO) {
            apiService.getItems()
        }
    }
}
