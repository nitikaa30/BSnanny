package com.example.bsnanny.repositories.nannies

import com.example.bsnanny.models.findNanny.FindNannyBody
import com.example.bsnanny.models.findNanny.FindNannyResponse
import com.example.bsnanny.retrofit.ApiHelper
import com.example.bsnanny.utils.NetworkResults
import javax.inject.Inject

class FindNannyRepository  @Inject constructor(
    private val apiHelper: ApiHelper
){
    suspend fun findNanny(findNannyBody: FindNannyBody) : NetworkResults<FindNannyResponse>{
        return apiHelper.findNanny(findNannyBody)
    }
}