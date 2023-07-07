package com.example.bsnanny.models.feedbackModel

import com.google.gson.annotations.SerializedName

data class Nany(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)