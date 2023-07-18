package com.example.bsnanny.models.requests.parent

data class ParentRequestsResponse(
    val `data`: List<Data>,
    val msg: String,
    val success: Boolean
)