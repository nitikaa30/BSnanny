package com.example.bsnanny.viewmodels.nannies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bsnanny.models.findNanny.FindNanny
import com.example.bsnanny.models.findNanny.FindNannyBody
import com.example.bsnanny.models.findNanny.FindNannyResponse
import com.example.bsnanny.repositories.nannies.FindNannyRepository
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.SingleMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindNannyViewModel @Inject constructor(
    private val findNannyRepository: FindNannyRepository
) : ViewModel(){
    private val _res = SingleMutableLiveData<NetworkResults<FindNannyResponse>>()
    val res: LiveData<NetworkResults<FindNannyResponse>>
        get() = _res

    fun findNannies(findNannyBody: FindNannyBody) = viewModelScope.launch {
        _res.postValue(NetworkResults.Loading())
        findNannyRepository.findNanny(findNannyBody).let {
            _res.postValue(it)
        }
    }
}