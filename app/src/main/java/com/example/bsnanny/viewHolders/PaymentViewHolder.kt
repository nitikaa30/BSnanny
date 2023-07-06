package com.example.bsnanny.viewHolders

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.bsnanny.adapter.payment.PaymentAdapter
import com.example.bsnanny.databinding.AddNewCardItemBinding
import com.example.bsnanny.databinding.BankCardItemsBinding
import com.example.bsnanny.models.payment.PaymentModel

sealed class PaymentViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    class CardViewHolder(private val binding: BankCardItemsBinding) :
        PaymentViewHolder(binding) {
        fun bind(
            itemType: PaymentModel.CardModel,
            adapter: PaymentAdapter,
            selectedPosition: Int
        ) {
            binding.paymentCardImage.setImageResource(itemType.cardPhoto)
            binding.masterCardAddress.text = itemType.cardName
            binding.cardRadioBtn.isChecked = adapter.getSelectedPosition() == bindingAdapterPosition
            binding.cardRadioBtn.setOnClickListener {
                val position = bindingAdapterPosition
                adapter.setSelectedPosition(position)
                adapter.notifyDataSetChanged()
            }
        }
    }

    class AddNewCardViewHolder(
        private val binding: AddNewCardItemBinding,
        private val onAddNewCardClicked: () -> Unit
    ) :
        PaymentViewHolder(binding) {
        init {
            binding.root.setOnClickListener {
                onAddNewCardClicked.invoke()
            }
        }

        fun bind(
            itemType: PaymentModel.AddNewCard
        ) {
            binding.addNewCardText.text = itemType.add
            binding.addMoreCards.setImageResource(itemType.photo)

        }
    }
}
