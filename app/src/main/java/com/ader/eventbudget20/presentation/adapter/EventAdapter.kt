package com.ader.eventbudget20.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ader.eventbudget20.domain.model.Event
import com.ader.eventbudget20.presentation.base.BaseRecyclerAdapter
import com.receipt.eventbudget20.databinding.ItemStringBinding

class EventAdapter: BaseRecyclerAdapter<Event>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = ItemStringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StringViewHolder(binding)
    }

    inner class StringViewHolder(private val binding: ItemStringBinding): BaseViewHolder(binding.root){
        override fun bind(item: Event) {
            binding.apply {
                tvItem.text = item.name
            }
        }
    }
}