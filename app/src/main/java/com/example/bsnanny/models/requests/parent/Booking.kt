package com.example.bsnanny.models.requests.parent

data class Booking(
    val Parent: Parent,
    val createdAt: String,
    val end_date: String,
    val from: String,
    val id: Int,
    val nany_id: Any,
    val no_of_children: Int,
    val parent_id: Int,
    val start_date: String,
    val status: Int,
    val to: String,
    val type: Any,
    val updatedAt: String
)