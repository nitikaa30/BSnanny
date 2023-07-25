package com.example.bsnanny.models.bookings.parentBooking.cancelBooking

import com.google.gson.annotations.SerializedName

data class CancelBookingBody(
    @SerializedName("booking_id")
    val bookingId : Int
)
