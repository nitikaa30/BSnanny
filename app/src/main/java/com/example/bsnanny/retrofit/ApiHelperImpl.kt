package com.example.bsnanny.retrofit

import com.example.bsnanny.models.authentication.AuthenticationBody
import com.example.bsnanny.models.authentication.AuthenticationResponse
import com.example.bsnanny.models.bookAppointment.BookAppointmentResponse
import com.example.bsnanny.models.bookAppointment.BookRequest
import com.example.bsnanny.models.checkUser.CheckUserBody
import com.example.bsnanny.models.checkUser.CheckUserResponse
import com.example.bsnanny.models.feedbackModel.FeedbackBody
import com.example.bsnanny.models.feedbackModel.FeedbackListResponse
import com.example.bsnanny.models.feedbackModel.FeedbackResponse
import com.example.bsnanny.models.requests.parent.Booking
import com.example.bsnanny.models.requests.parent.Data
import com.example.bsnanny.models.requests.parent.ParentRequestsResponse
import com.example.bsnanny.models.requests.parent.accept.AcceptResponse
import com.example.bsnanny.models.requests.parent.reject.RejectResponse
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.safeApiCall

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : ApiHelper {
    override suspend fun checkUser(checkUserBody: CheckUserBody): NetworkResults<CheckUserResponse> {

        return safeApiCall { apiInterface.checkUser(checkUserBody) }
    }

    override suspend fun authenticate(authenticationBody: AuthenticationBody): NetworkResults<AuthenticationResponse> {
        return safeApiCall {
            apiInterface.authenticate(authenticationBody)
        }
    }

    override suspend fun saveFeedback(
        feedback: FeedbackBody
    ): NetworkResults<FeedbackResponse> {
        return safeApiCall { apiInterface.saveFeedback(feedback) }
    }

    override suspend fun getFeedbackList(): NetworkResults<FeedbackListResponse> {
        return safeApiCall { apiInterface.getFeedbackList() }
    }

    override suspend fun getParentRequests(): NetworkResults<ParentRequestsResponse> {
        return safeApiCall { apiInterface.getParentRequests() }
    }

    override suspend fun acceptParentRequest(id: Data): NetworkResults<AcceptResponse> {
        return safeApiCall { apiInterface.acceptParentRequest(id.id) }
    }

    override suspend fun rejectParentRequest(id: Data): NetworkResults<RejectResponse> {
        return safeApiCall { apiInterface.rejectParentRequest(id.id) }
    }

    override suspend fun applytoFamilies(apply: BookRequest): NetworkResults<BookAppointmentResponse> {
        return safeApiCall { apiInterface.applytoFamiliy(apply) }
    }
}