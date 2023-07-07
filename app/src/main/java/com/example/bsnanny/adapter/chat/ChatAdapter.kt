package com.example.bsnanny.adapter.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bsnanny.databinding.ChatRecieverLayoutBinding
import com.example.bsnanny.databinding.ChatSenderLayoutBinding

class ChatAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_LEFT){
            val binding= ChatSenderLayoutBinding.inflate(inflater,parent,false)
            SenderViewHolder(binding)
        }else{
            val binding=ChatRecieverLayoutBinding.inflate(inflater,parent,false)
            ReceiverViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
    class SenderViewHolder(private val binding: ChatSenderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

//        fun bind(item: ChatData) {
//            binding.sender.text = item.text
//            binding.timestamp.text= item.timestamp.toString()
//        }
    }

    class ReceiverViewHolder(private val binding: ChatRecieverLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

//        fun bind(item: ChatData) {
//
//            binding.sender.text = item.text
//            binding.timestamp.text= item.timestamp.toString()
//        }
    }
    companion object {
        private const val VIEW_TYPE_LEFT = 0
        private const val VIEW_TYPE_RIGHT = 1
    }
}