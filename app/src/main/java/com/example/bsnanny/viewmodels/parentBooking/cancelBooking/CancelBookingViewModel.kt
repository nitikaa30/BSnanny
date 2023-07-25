package com.example.bsnanny.viewmodels.parentBooking.cancelBooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bsnanny.models.bookings.parentBooking.cancelBooking.CancelBookingBody
import com.example.bsnanny.models.bookings.parentBooking.cancelBooking.CancelBookingResponse
import com.example.bsnanny.repositories.parentBookings.canceBooking.CancelBookingRepository
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.SingleMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CancelBookingViewModel @Inject constructor(
    private val cancelBookingRepository: CancelBookingRepository
) : ViewModel(){

    private val _res = SingleMutableLiveData<NetworkResults<CancelBookingResponse>>()
     val res : LiveData<NetworkResults<CancelBookingResponse>>
         get() = _res

    fun cancelParentBooking(cancelBookingBody: CancelBookingBody) = viewModelScope.launch {
        _res.postValue(NetworkResults.Loading())
        cancelBookingRepository.cancelParentBooking(cancelBookingBody).let {
            _res.postValue(it)
        }
    }

}