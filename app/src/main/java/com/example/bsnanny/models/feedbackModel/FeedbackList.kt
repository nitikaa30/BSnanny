package com.example.bsnanny.models.feedbackModel

import com.google.gson.annotations.SerializedName

data class FeedbackList(
    val imageUrl: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("id")
    val id: Int,
    @SerializedName("booking_id")
    val bookingId: Int,
    @SerializedName("stars")
    val stars: Int,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("Nany")
    val nanny: Nany
)