package com.example.bsnanny.models.feedbackModel

import com.google.gson.annotations.SerializedName

data class FeedbackListResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("msg")
    val message: String,
    @SerializedName("feedbacks")
    val feedbacks: List<FeedbackList>
)