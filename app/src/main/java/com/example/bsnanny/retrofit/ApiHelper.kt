package com.example.bsnanny.retrofit

import com.example.bsnanny.models.checkUser.CheckUserBody
import com.example.bsnanny.models.checkUser.CheckUserResponse
import com.example.bsnanny.models.feedbackModel.Feedback
import com.example.bsnanny.models.feedbackModel.FeedbackListResponse
import com.example.bsnanny.models.feedbackModel.FeedbackResponse
import com.example.bsnanny.utils.NetworkResults
import retrofit2.http.Header


interface ApiHelper {
    suspend fun checkUser( checkUserBody: CheckUserBody) : NetworkResults<CheckUserResponse>

    suspend fun saveFeedback(@Header("Authorization") token:String,feedback: Feedback):NetworkResults<FeedbackResponse>

    suspend fun getFeedbackList(@Header("Authorization") token:String):NetworkResults<FeedbackListResponse>
}