package com.example.bsnanny.models.bookings.parentBooking

import com.google.gson.annotations.SerializedName

data class ParentBookingResponse(
    val success : Boolean,
    val msg : String,
    @SerializedName("data")
    val parentBookingData: ArrayList<ParentBookingData>
)
data class ParentBookingData(
    @SerializedName("status")
    val status: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("parent_id")
    val parentId: Int,
    @SerializedName("nany_id")
    val nannyId: Int?,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("type")
    val type: String?,
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("pin")
    val pin: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("no_of_children")
    val numberOfChildren: Int,
    @SerializedName("from")
    val from: String,
    @SerializedName("to")
    val to: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("Nany")
    val nanny: Any?,
    @SerializedName("Otp")
    val otp: Any?,
    @SerializedName("Progress")
    val progress: Any?

)
