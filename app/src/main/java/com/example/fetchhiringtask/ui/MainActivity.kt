package com.example.fetchhiringtask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchhiringtask.R
import com.example.fetchhiringtask.repository.ItemRepository
import com.example.fetchhiringtask.service.ApiService
import com.example.fetchhiringtask.ui.adapter.ItemAdapter
import com.example.fetchhiringtask.viewmodel.ItemViewModel
import com.example.fetchhiringtask.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private val viewModel: ItemViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(ItemRepository(ApiService.create())))
            .get(ItemViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ItemAdapter(emptyList())
        recyclerView.adapter = adapter

        viewModel.items.observe(this) { itemList ->
            adapter = ItemAdapter(itemList)
            recyclerView.adapter = adapter
        }
    }
}
