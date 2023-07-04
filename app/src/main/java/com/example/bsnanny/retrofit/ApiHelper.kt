package com.example.bsnanny.retrofit

import com.example.bsnanny.NetworkResults
import com.example.bsnanny.models.checkUser.CheckUserBody
import com.example.bsnanny.models.checkUser.CheckUserResponse
import com.example.bsnanny.models.feedbackModel.Feedback
import com.example.bsnanny.models.feedbackModel.FeedbackResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header


interface ApiHelper {
    suspend fun checkUser( checkUserBody: CheckUserBody) : NetworkResults<CheckUserResponse>
    suspend fun saveFeedback(@Header("Authorization") token:String,feedback: Feedback):NetworkResults<FeedbackResponse>
}