package com.ader.eventbudget20.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ader.eventbudget20.domain.model.User
import com.ader.eventbudget20.presentation.base.BaseRecyclerAdapter
import com.ader.eventbudget20.presentation.model.AddParticipantAdapterModel
import com.receipt.eventbudget20.databinding.ItemAddParticipantBinding

class AddParticipantAdapter: BaseRecyclerAdapter<AddParticipantAdapterModel>() {
    var onCheckedCheckChange: OnItemCheckChange? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = ItemAddParticipantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddParticipantViewHolder(binding)
    }

    inner class AddParticipantViewHolder(private val binding: ItemAddParticipantBinding): BaseViewHolder(binding.root){
        override fun bind(position: Int) {
            super.bind(position)
            binding.apply {
                chbParticipant.setOnCheckedChangeListener { _, isChecked ->
                    onCheckedCheckChange?.onItemCheckChange(position, isChecked)
                    data[position].isParticipant = isChecked
                }
            }
        }

        override fun bind(item: AddParticipantAdapterModel) {
            binding.apply {
                chbParticipant.setOnCheckedChangeListener(null)
                tvName.text = item.user.name
                chbParticipant.isChecked = item.isParticipant
            }
        }
    }

    interface OnItemCheckChange {
        fun onItemCheckChange(position: Int, checked: Boolean)
    }
}