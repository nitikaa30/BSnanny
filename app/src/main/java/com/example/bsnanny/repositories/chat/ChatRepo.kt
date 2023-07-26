package com.example.bsnanny.repositories.chat

import com.example.bsnanny.models.chat.ChatData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ChatRepo @Inject constructor(private val firestore: FirebaseFirestore) {
    suspend fun getChatMessages(senderId: String, receiverId: String): List<ChatData> = suspendCoroutine { continuation ->
        val messagesList = mutableListOf<ChatData>()

        val chatCollection = firestore.collection("chats")
        val query = chatCollection
            .whereEqualTo("senderId", senderId)
            .whereEqualTo("receiverId", receiverId)
            .orderBy("timestamp", Query.Direction.ASCENDING)

        query.get()
            .addOnSuccessListener { querySnapshot ->
                messagesList.clear()
                querySnapshot?.forEach { document ->
                    val message = document.toObject(ChatData::class.java)
                    messagesList.add(message)
                }
                continuation.resume(messagesList)
            }
            .addOnFailureListener { error ->
                continuation.resumeWithException(error)
            }
    }
}