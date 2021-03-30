package com.ader.eventbudget20.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ader.eventbudget20.domain.model.Payment
import com.ader.eventbudget20.presentation.base.BaseRecyclerAdapter
import com.receipt.eventbudget20.databinding.ItemPaymentBinding

class PaymentAdapter: BaseRecyclerAdapter<Payment>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = ItemPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentViewHolder(binding)
    }

    inner class PaymentViewHolder(private val binding: ItemPaymentBinding): BaseViewHolder(binding.root){
        override fun bind(item: Payment) {
            binding.apply {
                tvValue.text = item.value.toString()
                //tvTitle.text = "${item.payer.name}: ${item.description}"
                tvTitle.text = item.payersList.joinToString {
                    it.name
                } + ": ${item.description}"
                tvParticipants.text = item.paymentParticipants.joinToString {
                    it.name
                }
            }
        }
    }
}