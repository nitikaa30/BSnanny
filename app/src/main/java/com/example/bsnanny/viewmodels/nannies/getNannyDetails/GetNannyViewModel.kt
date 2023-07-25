package com.example.bsnanny.viewmodels.nannies.getNannyDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bsnanny.models.findNanny.getNanny.GetNannyResponse
import com.example.bsnanny.repositories.nannies.getNannyDetails.GetNannyDetailsRepository
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.SingleMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetNannyViewModel @Inject constructor(
    private val getNannyDetailsRepository: GetNannyDetailsRepository
) : ViewModel() {

    private val _res = SingleMutableLiveData<NetworkResults<GetNannyResponse>>()
    val res: LiveData<NetworkResults<GetNannyResponse>>
        get() = _res
    fun getNannyDetails(userId : String) = viewModelScope.launch {
        _res.postValue(NetworkResults.Loading())
        getNannyDetailsRepository.getNannyDetails(userId).let {
            _res.postValue(it)
        }
    }
}