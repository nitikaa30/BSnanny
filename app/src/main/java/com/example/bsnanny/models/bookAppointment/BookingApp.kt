package com.example.bsnanny.models.bookAppointment

data class BookingApp(
    val createdAt: String,
    val end_date: String,
    val id: Int,
    val nany_id: Int,
    val parent_id: String,
    val start_date: String,
    val updatedAt: String
)