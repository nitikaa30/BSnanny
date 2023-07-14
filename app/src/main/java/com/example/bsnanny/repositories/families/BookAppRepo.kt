package com.example.bsnanny.repositories.families

import com.example.bsnanny.models.bookAppointment.BookAppointmentResponse
import com.example.bsnanny.models.bookAppointment.BookRequest
import com.example.bsnanny.retrofit.ApiHelper
import com.example.bsnanny.utils.NetworkResults
import javax.inject.Inject

class BookAppRepo  @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun applytoFamilies(bookingApp: BookRequest):NetworkResults<BookAppointmentResponse>{
        return apiHelper.applytoFamilies(bookingApp)
    }
}