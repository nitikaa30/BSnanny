package com.example.bsnanny.views.fragments.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bsnanny.adapter.chat.ChatAdapter
import com.example.bsnanny.databinding.FragmentChatBinding
import com.example.bsnanny.models.chat.ChatData
import com.example.bsnanny.viewmodels.chat.ChatViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ChatFragment : Fragment(){
    private lateinit var binding : FragmentChatBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: ChatAdapter
    private lateinit var viewModel: ChatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        val senderId = arguments?.getString("senderId") ?: ""
        val receiverId = arguments?.getString("receiverId") ?: ""
        adapter= ChatAdapter(senderId, mutableListOf())
        binding.chatRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.chatRecyclerView.adapter=adapter
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        viewModel.res.observe(viewLifecycleOwner, Observer {
            adapter.setMessages(it)
        })
        viewModel.fetchChatMessages(senderId, receiverId)
        binding.sendMsgBtn.setOnClickListener {
            val message=binding.chatTypeMessage.toString().trim()
            if(message.isNotEmpty()){
                val data=ChatData(System.currentTimeMillis(),message,senderId.toInt(),receiverId.toInt())
                insertChatData(data)

            }
        }

    }
    private fun insertChatData(chatData: ChatData) {
        firestore.collection("chatMessages")
            .add(chatData)
            .addOnSuccessListener { documentReference ->
                Log.d("ChatMessage", "Chat message added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.e("ChatMessage", "Error adding chat message", e)
            }
    }
}