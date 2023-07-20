package com.example.bsnanny.models.bookings

data class Parent(
    val User: User,
    val child_category: Any,
    val id: Int,
    val user_id: Int
)