package com.example.bsnanny.viewmodels.requests

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bsnanny.models.requests.parent.Booking
import com.example.bsnanny.models.requests.parent.ParentRequestsResponse
import com.example.bsnanny.models.requests.parent.accept.AcceptResponse
import com.example.bsnanny.models.requests.parent.accept.Bookings
import com.example.bsnanny.models.requests.parent.reject.RejectResponse
import com.example.bsnanny.repositories.requests.RequestsRepo
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.SingleMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RequestViewModel @Inject constructor(private val requestsRepo: RequestsRepo) : ViewModel() {
    private val _res = SingleMutableLiveData<NetworkResults<ParentRequestsResponse>>()
    val res: LiveData<NetworkResults<ParentRequestsResponse>>
        get() = _res

    private val _new = SingleMutableLiveData<NetworkResults<AcceptResponse>>()
    val new: LiveData<NetworkResults<AcceptResponse>>
        get() = _new

    private val _abc = SingleMutableLiveData<NetworkResults<RejectResponse>>()
    val abc: LiveData<NetworkResults<RejectResponse>>
        get() = _abc

    fun getParentsRequests() = viewModelScope.launch {
        _res.postValue(NetworkResults.Loading())

        val result = requestsRepo.getParentRequests()
        _res.postValue(result)
    }
    fun acceptRequest(bookings: Booking)=viewModelScope.launch {
        _new.postValue(NetworkResults.Loading())
        val result=requestsRepo.acceptParentRequest(bookings)
        _new.postValue(result)
    }
    fun rejectRequest(bookings: Booking)=viewModelScope.launch {
        _abc.postValue(NetworkResults.Loading())
        val result=requestsRepo.rejectParentRequest(bookings)
        _abc.postValue(result)
    }
}
