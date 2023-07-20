package com.example.bsnanny.repositories.parentBookings

import com.example.bsnanny.models.bookings.parentBooking.ParentBookingResponse
import com.example.bsnanny.retrofit.ApiHelper
import com.example.bsnanny.utils.NetworkResults
import javax.inject.Inject

class ParentBookingsRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getBookingHistory() : NetworkResults<ParentBookingResponse>{
        return apiHelper.getBookingHistory()
    }
}