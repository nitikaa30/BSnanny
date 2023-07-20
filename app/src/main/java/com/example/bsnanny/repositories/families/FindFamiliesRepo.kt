package com.example.bsnanny.repositories.families

import com.example.bsnanny.models.findfamilies.FindFamilyResponse
import com.example.bsnanny.retrofit.ApiHelper
import com.example.bsnanny.utils.NetworkResults
import com.google.type.LatLng
import javax.inject.Inject

class FindFamiliesRepo @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun findParents(lat:Int,long:Int):NetworkResults<FindFamilyResponse>{
        return apiHelper.findParents(lat, long)
    }
}