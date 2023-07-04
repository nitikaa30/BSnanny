package com.example.bsnanny.repositories

import com.example.bsnanny.NetworkResults
import com.example.bsnanny.models.checkUser.CheckUserBody
import com.example.bsnanny.models.checkUser.CheckUserResponse
import com.example.bsnanny.retrofit.ApiHelper
import retrofit2.Response
import javax.inject.Inject

class CheckUserRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun checkUser(checkUserBody: CheckUserBody): NetworkResults<CheckUserResponse> {
        return apiHelper.checkUser(checkUserBody)
    }
}