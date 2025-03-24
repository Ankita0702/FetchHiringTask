package com.example.fetchhiringtask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fetchhiringtask.model.Item
import com.example.fetchhiringtask.repository.ItemRepository
import com.example.fetchhiringtask.ui.adapter.ListItem
import com.example.fetchhiringtask.viewmodel.ItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class ItemViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: ItemRepository
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadGroupedItems returns correctly grouped and sorted items`() = runTest {
        val mockItems = listOf(
            Item(1, 1, "Item 10"),
            Item(2, 1, "Item 2"),
            Item(3, 2, "Item 1"),
            Item(4, 1, null),
            Item(5, 1, "")
        )

        whenever(repository.fetchItems()).thenReturn(mockItems)

        val viewModel = ItemViewModel(repository)
        val result = viewModel.loadGroupedItems()

        assertEquals(5, result.size)
        assertTrue(result[0] is ListItem.Header && (result[0] as ListItem.Header).listId == 1)
        assertTrue(result[1] is ListItem.ItemData && (result[1] as ListItem.ItemData).item.name == "Item 2")
        assertTrue(result[2] is ListItem.ItemData && (result[2] as ListItem.ItemData).item.name == "Item 10")
        assertTrue(result[3] is ListItem.Header && (result[3] as ListItem.Header).listId == 2)
        assertTrue(result[4] is ListItem.ItemData && (result[4] as ListItem.ItemData).item.name == "Item 1")
    }

    @Test(expected = RuntimeException::class)
    fun `loadGroupedItems throws if repository fails`() = runTest {
        whenever(repository.fetchItems()).thenThrow(RuntimeException("Network error"))

        val viewModel = ItemViewModel(repository)

        viewModel.loadGroupedItems() // should throw
    }

    @Test
    fun `loadGroupedItems returns empty list when repository returns no data`() = runTest {
        whenever(repository.fetchItems()).thenReturn(emptyList())

        val viewModel = ItemViewModel(repository)
        val result = viewModel.loadGroupedItems()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `loadGroupedItems filters out all invalid items`() = runTest {
        val badItems = listOf(
            Item(1, 1, ""),
            Item(2, 1, null),
            Item(3, 2, "")
        )

        whenever(repository.fetchItems()).thenReturn(badItems)

        val viewModel = ItemViewModel(repository)
        val result = viewModel.loadGroupedItems()

        assertTrue(result.isEmpty()) // no valid items should remain
    }


}
