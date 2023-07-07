package com.example.bsnanny.models.requests.parent.accept

data class Bookings(
    val createdAt: String,
    val end_date: String,
    val from: String,
    val id: Int,
    val is_request_nany: Boolean,
    val nany_id: Int,
    val no_of_days: Int,
    val parent_id: Int,
    val start_date: String,
    val status: Boolean,
    val to: String,
    val type: Any,
    val updatedAt: String
)