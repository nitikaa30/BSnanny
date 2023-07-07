package com.example.bsnanny.models.requests.parent.accept

data class AcceptResponse(
    val booking: Bookings,
    val msg: String,
    val success: Boolean
)