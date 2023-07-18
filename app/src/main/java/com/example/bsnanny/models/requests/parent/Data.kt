package com.example.bsnanny.models.requests.parent

data class Data(
    val Booking: Booking,
    val booking_id: Int,
    val createdAt: String,
    val id: Int,
    val is_request_nany: Boolean,
    val nany_id: Int,
    val status: String,
    val updatedAt: String
)