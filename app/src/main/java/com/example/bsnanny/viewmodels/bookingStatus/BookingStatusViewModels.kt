package com.example.bsnanny.viewmodels.bookingStatus

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bsnanny.models.pendingBookings.PendingBookingResponse

import com.example.bsnanny.repositories.bookingStatus.BookingStatusRepository
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.SingleMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingStatusViewModels @Inject constructor(
    private val bookingStatusRepository: BookingStatusRepository
) :ViewModel(){

    private val _res = SingleMutableLiveData<NetworkResults<PendingBookingResponse>>()
    val res: LiveData<NetworkResults<PendingBookingResponse>>
        get() = _res

    fun getBookingStatus() = viewModelScope.launch {
        _res.postValue(NetworkResults.Loading())
        bookingStatusRepository.bookingStatus().let {
            _res.postValue(it)
        }
    }
}