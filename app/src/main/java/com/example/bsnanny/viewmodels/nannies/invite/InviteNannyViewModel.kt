package com.example.bsnanny.viewmodels.nannies.invite


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bsnanny.models.findNanny.inviteNanny.InviteNannyBody
import com.example.bsnanny.models.findNanny.inviteNanny.InviteNannyResponse
import com.example.bsnanny.repositories.nannies.invite.InviteNannyRepository
import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.utils.SingleMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InviteNannyViewModel @Inject constructor(
    private val inviteNannyRepository : InviteNannyRepository
) : ViewModel(){

    private val _res = SingleMutableLiveData<NetworkResults<InviteNannyResponse>>()
    val res : LiveData<NetworkResults<InviteNannyResponse>>
        get() = _res

    fun inviteNanny(inviteNannyBody: InviteNannyBody) = viewModelScope.launch {
        _res.postValue(NetworkResults.Loading())
        inviteNannyRepository.inviteNanny(inviteNannyBody).let {
            _res.postValue(it)
        }
    }
}