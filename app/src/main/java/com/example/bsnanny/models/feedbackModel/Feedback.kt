package com.example.bsnanny.models.feedbackModel

data class Feedback(
    val id: Int,
    val parentId: Int,
    val nannyId: Int,
    val bookingId: Int,
    val stars: String,
    val comment: String,
    val updatedAt: String,
    val createdAt: String
)