package com.example.bsnanny.models

data class InvitationModel(
    val photoUri : Int,
    val name : String,
    val status : String,
    val country : String,
    val nannyAge : Int,
    val experience : String,
    val charge : Int
)
