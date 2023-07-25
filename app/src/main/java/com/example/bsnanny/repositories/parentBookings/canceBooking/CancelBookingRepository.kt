package com.example.bsnanny.repositories.parentBookings.canceBooking

import com.example.bsnanny.models.bookings.parentBooking.cancelBooking.CancelBookingBody
import com.example.bsnanny.models.bookings.parentBooking.cancelBooking.CancelBookingResponse
import com.example.bsnanny.retrofit.ApiHelper
import com.example.bsnanny.utils.NetworkResults
import javax.inject.Inject

class CancelBookingRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun cancelParentBooking(cancelBookingBody: CancelBookingBody) : NetworkResults<CancelBookingResponse>{
        return apiHelper.cancelParentBookings(cancelBookingBody)
    }
}