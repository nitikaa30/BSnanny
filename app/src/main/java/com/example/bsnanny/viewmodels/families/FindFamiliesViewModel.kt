package com.example.bsnanny.viewmodels.families

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bsnanny.models.findfamilies.FindFamilyResponse
import com.example.bsnanny.repositories.families.FindFamiliesRepo
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.SingleMutableLiveData
import kotlinx.coroutines.launch
import javax.inject.Inject

class FindFamiliesViewModel @Inject constructor
    (private val findFamiliesRepo: FindFamiliesRepo): ViewModel() {
    private val _res=SingleMutableLiveData<NetworkResults<FindFamilyResponse>>()
        val res:LiveData<NetworkResults<FindFamilyResponse>>
            get() = _res

    fun find(lat:Int,long:Int)=viewModelScope.launch {
        findFamiliesRepo.findParents(lat, long).let {
            _res.postValue(it)
        }
    }
}