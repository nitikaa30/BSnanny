package com.example.bsnanny.viewmodels.parentBooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bsnanny.models.bookings.parentBooking.ParentBookingResponse

import com.example.bsnanny.repositories.parentBookings.ParentBookingsRepository
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.SingleMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParentBookingsViewModel @Inject constructor(
    private val parentBookingsRepository: ParentBookingsRepository
) : ViewModel() {

    private val _res = SingleMutableLiveData<NetworkResults<ParentBookingResponse>>()
    val res: LiveData<NetworkResults<ParentBookingResponse>>
        get() = _res

    fun getBookingHistory() = viewModelScope.launch {
        _res.postValue(NetworkResults.Loading())

        parentBookingsRepository.getBookingHistory().let {
            _res.postValue(it)
        }
    }
}