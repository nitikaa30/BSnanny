package com.example.bsnanny.viewmodels.families

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bsnanny.models.bookAppointment.BookAppointmentResponse
import com.example.bsnanny.models.bookAppointment.BookRequest
import com.example.bsnanny.repositories.families.BookAppRepo
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.SingleMutableLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookAppointmentViewModel @Inject constructor(private val bookAppRepo: BookAppRepo):ViewModel(){
    private val _res=SingleMutableLiveData<NetworkResults<BookAppointmentResponse>>()
    val res:LiveData<NetworkResults<BookAppointmentResponse>>
    get() = _res

    fun apply(parent_id: String, start_date:String, end_date: String)= viewModelScope.launch {
        val apply= BookRequest(parent_id, start_date,end_date)
        bookAppRepo.applytoFamilies(apply).let{
            _res.postValue(it)
        }
    }
}