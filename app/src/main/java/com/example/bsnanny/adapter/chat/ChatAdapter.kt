package com.example.bsnanny.adapter.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bsnanny.databinding.ChatRecieverLayoutBinding
import com.example.bsnanny.databinding.ChatSenderLayoutBinding
import com.example.bsnanny.models.chat.ChatData

class ChatAdapter(private val currentUserId: String,private var items: List<ChatData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is SenderViewHolder) {
            holder.bind(item)
        } else if (holder is ReceiverViewHolder) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_LEFT -> {
                val binding = ChatSenderLayoutBinding.inflate(inflater, parent, false)
                SenderViewHolder(binding)
            }
            VIEW_TYPE_RIGHT -> {
                val binding = ChatRecieverLayoutBinding.inflate(inflater, parent, false)
                ReceiverViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    override fun getItemViewType(position: Int): Int {
        val message = items[position]
        return if (message.senderID.toString() == currentUserId) {
            VIEW_TYPE_LEFT
        } else {
            VIEW_TYPE_RIGHT
        }
    }

    class SenderViewHolder(private val binding: ChatSenderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChatData) {
            binding.senderMessage.text = item.text
            binding.senderTime.text= item.timestamp.toString()
        }
    }

    class ReceiverViewHolder(private val binding: ChatRecieverLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChatData) {

            binding.chatReceiverMsg.text = item.text
            binding.chatReceiverTime.text= item.timestamp.toString()
        }
    }
    fun setMessages(messages: List<ChatData>) {
        this.items=messages
        notifyDataSetChanged()
    }
    companion object {
        private const val VIEW_TYPE_LEFT = 0
        private const val VIEW_TYPE_RIGHT = 1
    }
}