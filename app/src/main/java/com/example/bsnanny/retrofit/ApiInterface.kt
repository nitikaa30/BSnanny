package com.example.bsnanny.retrofit

import com.example.bsnanny.models.authentication.AuthenticationBody
import com.example.bsnanny.models.authentication.AuthenticationResponse
import com.example.bsnanny.models.checkUser.CheckUserBody
import com.example.bsnanny.models.checkUser.CheckUserResponse
import com.example.bsnanny.models.feedbackModel.FeedbackBody
import com.example.bsnanny.models.feedbackModel.FeedbackListResponse
import com.example.bsnanny.models.feedbackModel.FeedbackResponse
import com.example.bsnanny.models.requests.parent.ParentRequestsResponse
import com.example.bsnanny.models.requests.parent.accept.AcceptResponse
import com.example.bsnanny.models.requests.parent.reject.RejectResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @POST("user/find_number")
    suspend fun checkUser(@Body checkUserBody: CheckUserBody): Response<CheckUserResponse>

    @POST("user/authenticate")
    suspend fun authenticate(@Body authenticationBody: AuthenticationBody) : Response<AuthenticationResponse>
    @POST("feedback/save")
    suspend fun saveFeedback(@Body feedback: FeedbackBody): Response<FeedbackResponse>

    @GET("feedback/list_feedbacks")
    suspend fun getFeedbackList():Response<FeedbackListResponse>

    @GET("nany/get_parent_requests")
    suspend fun getParentRequests():Response<ParentRequestsResponse>

    @POST("nany/accept")
    suspend fun acceptParentRequest(id:Int): Response<AcceptResponse>

    @POST("nany/reject")
    suspend fun rejectParentRequest():Response<RejectResponse>

}