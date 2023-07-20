package com.example.bsnanny.repositories.bookingStatus

import com.example.bsnanny.models.bookings.BookingsResponse
import com.example.bsnanny.models.pendingBookings.PendingBookingResponse
import com.example.bsnanny.retrofit.ApiHelper
import com.example.bsnanny.utils.NetworkResults
import javax.inject.Inject

class BookingStatusRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun bookingStatus() : NetworkResults<PendingBookingResponse>{
        return apiHelper.bookingStatus()
    }
}