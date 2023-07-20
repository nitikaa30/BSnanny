package com.example.bsnanny.models.findfamilies

data class FindFamilyResponse(
    val `data`: List<Data>,
    val msg: String,
    val success: Boolean
)