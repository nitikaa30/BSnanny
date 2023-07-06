package com.example.bsnanny.viewmodels.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bsnanny.di.ABC
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.SingleMutableLiveData
import com.example.bsnanny.models.authentication.AuthenticationBody
import com.example.bsnanny.models.authentication.AuthenticationResponse
import com.example.bsnanny.repositories.authentication.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel(){
    private val _res = SingleMutableLiveData<NetworkResults<AuthenticationResponse>>()
    val res: LiveData<NetworkResults<AuthenticationResponse>>
        get() = _res
    fun authenticate(authenticationBody: AuthenticationBody) = viewModelScope.launch{
        _res.postValue(NetworkResults.Loading())
        authenticationRepository.authenticate(authenticationBody).let {
            _res.postValue(it)
        }
    }

}