package com.example.bsnanny.retrofit.feedback

import com.example.bsnanny.models.feedbackModel.Feedback
import com.example.bsnanny.models.feedbackModel.FeedbackBody
import com.example.bsnanny.models.feedbackModel.FeedbackListResponse
import com.example.bsnanny.models.feedbackModel.FeedbackResponse
import com.example.bsnanny.retrofit.ApiHelper
import com.example.bsnanny.utils.NetworkResults
import retrofit2.http.Header
import javax.inject.Inject

class FeedbackRepo @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun saveFeedback(feedback: FeedbackBody): NetworkResults<FeedbackResponse>{
        return apiHelper.saveFeedback(feedback)
    }
    suspend fun getFeedbackList(): NetworkResults<FeedbackListResponse> {
        return apiHelper.getFeedbackList()
    }
}