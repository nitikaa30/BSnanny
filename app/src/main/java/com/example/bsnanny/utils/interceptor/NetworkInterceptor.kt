package com.example.bsnanny.utils.interceptor

import android.content.Context
import android.widget.Toast
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NetworkInterceptor(private val authToken: String) : Interceptor {
    private lateinit var context: Context
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val authorizedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $authToken")
            .build()

        val response = chain.proceed(authorizedRequest)
        if (response.code == 404) {
            Toast.makeText(context,"Session Expired",Toast.LENGTH_LONG).show()
        }else{
            return response
        }

        return response
    }

}