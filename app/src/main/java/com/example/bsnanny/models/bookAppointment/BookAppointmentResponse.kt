package com.example.bsnanny.models.bookAppointment

data class BookAppointmentResponse(
    val booking: BookingApp,
    val msg: String,
    val sucess: Boolean
)