package com.ader.eventbudget20.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ader.eventbudget20.domain.model.User
import com.ader.eventbudget20.presentation.base.BaseRecyclerAdapter
import com.ader.eventbudget20.presentation.model.ParticipantUIModel
import com.receipt.eventbudget20.databinding.ItemStringBinding

class ParticipantAdapter: BaseRecyclerAdapter<ParticipantUIModel>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = ItemStringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParticipantViewHolder(binding)
    }

    inner class ParticipantViewHolder(private val binding: ItemStringBinding): BaseViewHolder(binding.root){
        override fun bind(item: ParticipantUIModel) {
            binding.apply {
                tvItem.text = item.user.name
                tvTotalValue.text = item.total.toString()
            }
        }
    }
}