package com.example.bsnanny.models.bookAppointment

data class BookRequest(
    val parent_id: String,
    val start_date: String,
    val end_date: String
)
