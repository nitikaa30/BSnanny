package com.example.bsnanny.repositories.nannies.getNannyDetails

import com.example.bsnanny.models.findNanny.getNanny.GetNannyResponse
import com.example.bsnanny.retrofit.ApiHelper
import com.example.bsnanny.utils.NetworkResults
import javax.inject.Inject

class GetNannyDetailsRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getNannyDetails(userId : String) : NetworkResults<GetNannyResponse>{
        return apiHelper.getNannyDetails(userId)
    }
}