package com.example.bsnanny.models.authentication

import com.google.gson.annotations.SerializedName

data class AuthenticationBody(
    @SerializedName("phone_number")
    val phoneNumber : String,
    @SerializedName("account_type")
    val accountType : Int ?= null
)
