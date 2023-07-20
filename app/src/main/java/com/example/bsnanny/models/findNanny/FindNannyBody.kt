package com.example.bsnanny.models.findNanny

import com.google.gson.annotations.SerializedName

data class FindNannyBody(
    val latitude : String,
    val longitude : String,
    val price : String,
    @SerializedName("no_of_children")
    val noOfChildren : Int,
    val from : String,
    val to : String,
    @SerializedName("start_date")
    val startDate : String,
    @SerializedName("end_date")
    val endDate : String,
    val city : String,
    val pin : String,
    val country : String,
    val address : String
)
