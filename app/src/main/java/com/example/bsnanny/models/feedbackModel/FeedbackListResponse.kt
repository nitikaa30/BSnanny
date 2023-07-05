package com.example.bsnanny.models.feedbackModel

data class FeedbackListResponse(
    val feedbacks: List<FeedbackList>,
    val msg: String,
    val sucess: Boolean
)