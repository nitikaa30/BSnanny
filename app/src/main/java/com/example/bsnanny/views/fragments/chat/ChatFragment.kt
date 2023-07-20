package com.example.bsnanny.views.fragments.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bsnanny.adapter.chat.ChatAdapter
import com.example.bsnanny.databinding.FragmentChatBinding
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

    }
}