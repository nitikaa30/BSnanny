package com.example.bsnanny.models.findNanny.getNanny

import com.google.gson.annotations.SerializedName

data class GetNannyResponse (
    val success : Boolean,
    val msg : String,
    @SerializedName("data")
    val nannyBookingData: NannyBookingData
)
data class NannyBookingData(
    @SerializedName("booking")
    val nannyBooking: NannyBooking,
    @SerializedName("nany")
    val nany: Nany
)
data class NannyBooking(
    val status: String,
    val id: Int,
    @SerializedName("parent_id")
    val parentId: Int,
    @SerializedName("nany_id")
    val nanyId: Int?,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
    val type: String?,
    val address: String,
    val city: String,
    val latitude: Double,
    val longitude: Double,
    val pin: String,
    val country: String,
    val price: Int,
    @SerializedName("no_of_children")
    val noOfChildren: Int,
    val from: String,
    val to: String,
    val createdAt: String,
    val updatedAt: String
)

data class Nany(
    val age: Int,
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String,
    val address: String,
    val city: String,
    @SerializedName("postal_code")
    val postalCode: String,
    val gender: String,
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
    val description: String,
    val price: Int,
    @SerializedName("average_rating")
    val averageRating: Double,
    val experience: Int,
    @SerializedName("Nany")
    val nany: NanyDetails
)
data class NanyDetails(
    val id: Int,
    @SerializedName("Feedbacks")
    val feedbacksData: ArrayList<FeedbackData>
)
data class FeedbackData(
    val stars: Int,
    val comment: String,
    val createdAt: String,
    @SerializedName("Parent")
    val parent: Parent
)
data class Parent(
    val id: Int,
    @SerializedName("User")
    val user: User
)

data class User(
    val name: String,
    val avatar: String
)