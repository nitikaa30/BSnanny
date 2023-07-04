package com.example.bsnanny.models.checkUser

import com.google.gson.annotations.SerializedName

data class CheckUserBody(
    @SerializedName("phone_number")
    val phoneNumber : String
)
