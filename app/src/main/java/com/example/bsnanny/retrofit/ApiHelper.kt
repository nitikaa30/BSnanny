package com.example.bsnanny.retrofit

import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.models.authentication.AuthenticationBody
import com.example.bsnanny.models.authentication.AuthenticationResponse
import com.example.bsnanny.models.checkUser.CheckUserBody
import com.example.bsnanny.models.checkUser.CheckUserResponse
import com.example.bsnanny.models.feedbackModel.Feedback
import com.example.bsnanny.models.feedbackModel.FeedbackListResponse
import com.example.bsnanny.models.feedbackModel.FeedbackResponse



interface ApiHelper {
    suspend fun checkUser( checkUserBody: CheckUserBody) : NetworkResults<CheckUserResponse>

    suspend fun authenticate(authenticationBody: AuthenticationBody) : NetworkResults<AuthenticationResponse>

    suspend fun saveFeedback(feedback: Feedback):NetworkResults<FeedbackResponse>

    suspend fun getFeedbackList():NetworkResults<FeedbackListResponse>
}