package com.example.bsnanny.models.requests.parent

data class Parent(
    val Age: Age,
    val User: User,
    val child_category: Int,
    val id: Int,
    val no_of_children: Int,
    val user_id: Int
)