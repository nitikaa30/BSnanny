package com.example.bsnanny.retrofit

import com.example.bsnanny.NetworkResults
import com.example.bsnanny.models.checkUser.CheckUserBody
import com.example.bsnanny.models.checkUser.CheckUserResponse
import retrofit2.Response


interface ApiHelper {
    suspend fun checkUser( checkUserBody: CheckUserBody) : NetworkResults<CheckUserResponse>
}