package com.example.bsnanny.models.feedbackModel

data class FeedbackResponse(
    val success : Boolean,
    val msg : String,
    val feedback: Feedback
)
