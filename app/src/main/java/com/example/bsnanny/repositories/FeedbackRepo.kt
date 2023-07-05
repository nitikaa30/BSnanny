package com.example.bsnanny.repositories

import com.example.bsnanny.models.feedbackModel.Feedback
import com.example.bsnanny.models.feedbackModel.FeedbackListResponse
import com.example.bsnanny.models.feedbackModel.FeedbackResponse
import com.example.bsnanny.retrofit.ApiHelper
import com.example.bsnanny.utils.NetworkResults
import retrofit2.http.Header
import javax.inject.Inject

class FeedbackRepo @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun saveFeedback(@Header("Authorization") token:String, feedback: Feedback): NetworkResults<FeedbackResponse>{
        return apiHelper.saveFeedback(token, feedback)
    }
    suspend fun getFeedbackList(@Header("Authorization") token:String): NetworkResults<FeedbackListResponse> {
        return apiHelper.getFeedbackList(token)
    }
}