package com.example.bsnanny.retrofit

import com.example.bsnanny.NetworkResults
import com.example.bsnanny.models.checkUser.CheckUserBody
import com.example.bsnanny.models.checkUser.CheckUserResponse
import com.example.bsnanny.models.feedbackModel.Feedback
import com.example.bsnanny.models.feedbackModel.FeedbackResponse
import com.example.bsnanny.safeApiCall
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : ApiHelper {
    override suspend fun checkUser(checkUserBody: CheckUserBody): NetworkResults<CheckUserResponse> {

        return safeApiCall {apiInterface.checkUser(checkUserBody)}
    }

    override suspend fun saveFeedback(
        token: String,
        feedback: Feedback
    ): NetworkResults<FeedbackResponse> {
        return safeApiCall {apiInterface.saveFeedback(token,feedback)}
    }
}