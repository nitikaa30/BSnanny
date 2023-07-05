package com.example.bsnanny.repositories.authentication

import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.models.authentication.AuthenticationBody
import com.example.bsnanny.models.authentication.AuthenticationResponse
import com.example.bsnanny.retrofit.ApiHelper
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun authenticate(authenticationBody: AuthenticationBody) : NetworkResults<AuthenticationResponse> {
        return apiHelper.authenticate(authenticationBody)
    }
}