package com.example.bsnanny.models.authentication

import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    val success : Boolean,
    val msg : String,
    @SerializedName("data")
    val authenticationData : AuthenticationData
)
data class AuthenticationData(
    @SerializedName("user_id")
    val userId : String,
    @SerializedName("phone_number")
    val phoneNumber : String,
    @SerializedName("account_type")
    val accountType : Int ?= null,
    val token : String?
)
