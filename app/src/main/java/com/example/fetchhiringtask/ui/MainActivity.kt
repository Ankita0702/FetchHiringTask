package com.example.fetchhiringtask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchhiringtask.databinding.ActivityMainBinding
import com.example.fetchhiringtask.repository.ItemRepository
import com.example.fetchhiringtask.service.ApiService
import com.example.fetchhiringtask.ui.adapter.ItemAdapter
import com.example.fetchhiringtask.viewmodel.ItemViewModel
import com.example.fetchhiringtask.viewmodel.ItemViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: ItemViewModel by viewModels {
        ItemViewModelFactory(ItemRepository(ApiService.create()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        lifecycleScope.launch {
            val groupedItems = viewModel.loadGroupedItems()
            binding.recyclerView.adapter = ItemAdapter(groupedItems)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
    }
}
