package com.example.fetchhiringtask.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchhiringtask.R
import com.example.fetchhiringtask.model.Item

sealed class ListItem {
    data class Header(val listId: Int) : ListItem()
    data class ItemData(val item: Item) : ListItem()
}

class ItemAdapter(private val groupedItems: List<ListItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (groupedItems[position]) {
            is ListItem.Header -> VIEW_TYPE_HEADER
            is ListItem.ItemData -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            ItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = groupedItems[position]) {
            is ListItem.Header -> (holder as HeaderViewHolder).bind(item.listId)
            is ListItem.ItemData -> (holder as ItemViewHolder).bind(item.item)
        }
    }

    override fun getItemCount(): Int = groupedItems.size

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val listIdTextView: TextView = view.findViewById(R.id.listIdText)
        fun bind(listId: Int) {
            listIdTextView.text = itemView.context.getString(R.string.list_id, listId)
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.nameText)
        fun bind(item: Item) {
            nameTextView.text = item.name
        }
    }
}
