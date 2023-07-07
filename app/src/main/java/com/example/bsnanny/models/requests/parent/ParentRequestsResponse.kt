package com.example.bsnanny.models.requests.parent

data class ParentRequestsResponse(
    val bookings: List<Booking>,
    val msg: String,
    val success: Boolean
)