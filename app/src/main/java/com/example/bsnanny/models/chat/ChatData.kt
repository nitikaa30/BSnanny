package com.example.bsnanny.models.chat

data class ChatData(
    val timestamp: Long = System.currentTimeMillis(),
    val text: String,
    val senderID: Int
)
