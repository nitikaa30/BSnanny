package com.example.bsnanny.repositories.requests

import com.example.bsnanny.models.requests.parent.Booking
import com.example.bsnanny.models.requests.parent.ParentRequestsResponse
import com.example.bsnanny.models.requests.parent.accept.AcceptResponse
import com.example.bsnanny.models.requests.parent.accept.Bookings
import com.example.bsnanny.models.requests.parent.reject.RejectResponse
import com.example.bsnanny.retrofit.ApiHelper
import com.example.bsnanny.utils.NetworkResults
import javax.inject.Inject

class RequestsRepo @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getParentRequests():NetworkResults<ParentRequestsResponse>{
        return apiHelper.getParentRequests()
    }
    suspend fun acceptParentRequest(id: Booking):NetworkResults<AcceptResponse>{
        return apiHelper.acceptParentRequest(id)
    }

    suspend fun rejectParentRequest(id: Booking):NetworkResults<RejectResponse>{
        return apiHelper.rejectParentRequest(id)
    }
}