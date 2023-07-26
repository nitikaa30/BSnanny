package com.example.bsnanny.viewmodels.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bsnanny.models.bookAppointment.BookAppointmentResponse
import com.example.bsnanny.models.chat.ChatData
import com.example.bsnanny.repositories.chat.ChatRepo
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.SingleMutableLiveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel @Inject constructor(private val repo: ChatRepo): ViewModel() {
    private val _res= SingleMutableLiveData<List<ChatData>>()
    val res: LiveData<List<ChatData>>
        get() = _res

    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    fun fetchChatMessages(senderId: String, receiverId: String) {
        viewModelScope.launch {
            repo.getChatMessages(senderId, receiverId).let { messages ->
                _res.postValue(messages)
            }
        }
    }

}