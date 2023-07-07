package com.example.bsnanny.models.feedbackModel

import com.google.gson.annotations.SerializedName

data class FeedbackBody(
    @SerializedName("booking_id") val bookingId: Int,
    val stars:Int,
    val comment: String
)
