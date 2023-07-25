package com.example.bsnanny.repositories.nannies.invite

import com.example.bsnanny.models.findNanny.inviteNanny.InviteNannyBody
import com.example.bsnanny.models.findNanny.inviteNanny.InviteNannyResponse
import com.example.bsnanny.retrofit.ApiHelper
import com.example.bsnanny.utils.NetworkResults
import javax.inject.Inject

class InviteNannyRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun inviteNanny(inviteNannyBody: InviteNannyBody) : NetworkResults<InviteNannyResponse>{
        return apiHelper.inviteNanny(inviteNannyBody)
    }
}