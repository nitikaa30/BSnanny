package com.example.bsnanny.adapter.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bsnanny.R
import com.example.bsnanny.databinding.AddNewCardItemBinding
import com.example.bsnanny.databinding.BankCardItemsBinding
import com.example.bsnanny.models.payment.PaymentModel
import com.example.bsnanny.viewHolders.PaymentViewHolder

class PaymentAdapter(private val onAddNewCardClicked: () -> Unit) : RecyclerView.Adapter<PaymentViewHolder>() {
    private var selectedPosition = -1
    private var items = ArrayList<PaymentModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun submitData(list: ArrayList<PaymentModel>) {
        items.clear()
        items.addAll(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        return when (viewType) {
            R.layout.bank_card_items -> PaymentViewHolder.CardViewHolder(
                BankCardItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            R.layout.add_new_card_item -> PaymentViewHolder.AddNewCardViewHolder(
                AddNewCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onAddNewCardClicked
            )

            else -> {
                throw IllegalArgumentException("Illegal View type Provider")
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is PaymentViewHolder.CardViewHolder -> {
                holder.bind(item as PaymentModel.CardModel, this, getSelectedPosition())
            }
            is PaymentViewHolder.AddNewCardViewHolder ->{
                holder.bind(item as PaymentModel.AddNewCard)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is PaymentModel.CardModel -> R.layout.bank_card_items
            is PaymentModel.AddNewCard -> R.layout.add_new_card_item
        }
    }
    fun getSelectedPosition(): Int {
        return selectedPosition
    }

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
    }
}