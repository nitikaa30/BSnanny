package com.example.bsnanny.viewmodels

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bsnanny.NetworkResults
import com.example.bsnanny.SingleMutableLiveData
import com.example.bsnanny.models.checkUser.CheckUserBody
import com.example.bsnanny.models.checkUser.CheckUserResponse
import com.example.bsnanny.repositories.CheckUserRepository
import com.example.bsnanny.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CheckUserViewModel @Inject constructor(
    private val checkUserRepository: CheckUserRepository,
) : ViewModel() {
    private val _res = SingleMutableLiveData<NetworkResults<CheckUserResponse>>()
    val res: LiveData<NetworkResults<CheckUserResponse>>
        get() = _res

    fun checkUserData(checkUserBody: CheckUserBody) = viewModelScope.launch {
        _res.postValue(NetworkResults.Loading())
        checkUserRepository.checkUser(checkUserBody).let {
            _res.postValue(it)
        }
    }
}