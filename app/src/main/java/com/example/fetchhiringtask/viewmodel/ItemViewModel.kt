package com.example.fetchhiringtask.viewmodel

import androidx.lifecycle.ViewModel
import com.example.fetchhiringtask.model.Item
import com.example.fetchhiringtask.repository.ItemRepository
import com.example.fetchhiringtask.ui.adapter.ListItem

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    suspend fun loadGroupedItems(): List<ListItem> {
        val rawItems = repository.fetchItems()
        return groupItemsByListId(rawItems)
    }

    private fun groupItemsByListId(items: List<Item>): List<ListItem> {
        val groupedList = mutableListOf<ListItem>()
        val groupedMap = items
            .filter { !it.name.isNullOrBlank() }
            .groupBy { it.listId }
            .toSortedMap()

        groupedMap.forEach { (listId, itemList) ->
            groupedList.add(ListItem.Header(listId))
            itemList
                .sortedBy { extractItemNumber(it.name) }
                .forEach { groupedList.add(ListItem.ItemData(it)) }
        }

        return groupedList
    }

    private fun extractItemNumber(name: String?): Int {
        return name?.substringAfter("Item ")?.toIntOrNull() ?: Int.MAX_VALUE
    }
}
