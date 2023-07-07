package com.example.bsnanny.viewmodels.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bsnanny.models.feedbackModel.FeedbackBody
import com.example.bsnanny.models.feedbackModel.FeedbackListResponse
import com.example.bsnanny.models.feedbackModel.FeedbackResponse
import com.example.bsnanny.repositories.feedback.FeedbackRepo
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.SingleMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor
    (private val feedbackRepo: FeedbackRepo):ViewModel() {
        private val _res=SingleMutableLiveData<NetworkResults<FeedbackResponse>>()
    private val _new=SingleMutableLiveData<NetworkResults<FeedbackListResponse>>()
    val res: LiveData<NetworkResults<FeedbackResponse>>
    get()=_res
    val new: LiveData<NetworkResults<FeedbackListResponse>>
    get()=_new
    fun save(comment: String, stars: Int, bookingId: Int, token: String)=viewModelScope.launch {
        val feedback=FeedbackBody(bookingId, stars, comment)
        feedbackRepo.saveFeedback(feedback).let {
            _res.postValue(it)
        }
    }

    fun getList()=viewModelScope.launch {
        feedbackRepo.getFeedbackList().let {
            _new.postValue(it)
        }
    }
}