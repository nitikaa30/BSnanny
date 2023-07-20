package com.example.bsnanny.models.findNanny

import com.google.gson.annotations.SerializedName

data class FindNannyResponse(
    val success: Boolean,
    val msg: String,
    @SerializedName("data")
    val nanny: Nanny
)

data class Nanny(
    @SerializedName("booking")
    val bookingData: BookingData,
    @SerializedName("nanies")
    val nannies: ArrayList<FindNannyData>
)

data class BookingData(
    val status: String,
    val id: Int,
    @SerializedName("parent_id")
    val parentId: Int,
    @SerializedName("nany_id")
    val nannyId: Int?,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
    val from: String,
    val to: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val city: String,
    val pin: String,
    val country: String,
    val price: String,
    @SerializedName("no_of_children")
    val noOfChildren: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("createdAt")
    val createdAt: String
)

data class FindNannyData(
    val age: Int,
    @SerializedName("nany_id")
    val nannyId: Int,
    val id: Int,
    val name: String,
    val avatar: String,
    val email: String,
    val address: String,
    val city: String,
    @SerializedName("postal_code")
    val postalCode: String?,
    @SerializedName("date_of_birth")
    val dateOfBirth: String?,
    val description: String?,
    val distance: Double,
    val price: Int,
    @SerializedName("average_rating")
    val averageRating: Double?,
    val experience: Int
)

