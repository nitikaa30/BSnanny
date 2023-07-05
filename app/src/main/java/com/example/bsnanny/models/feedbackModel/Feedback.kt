package com.example.bsnanny.models.feedbackModel

data class Feedback(
    val booking_id: Int,
    val comment: String,
    val id: Int,
    val nany_id: Int,
    val parent_id: Int,
    val stars: String
)