package com.example.bsnanny.retrofit

import com.example.bsnanny.models.checkUser.CheckUserBody
import com.example.bsnanny.models.checkUser.CheckUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("user/find_number")
    suspend fun checkUser(@Body checkUserBody: CheckUserBody): Response<CheckUserResponse>
}