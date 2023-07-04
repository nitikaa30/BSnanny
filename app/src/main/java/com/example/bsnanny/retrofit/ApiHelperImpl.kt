package com.example.bsnanny.retrofit

import com.example.bsnanny.utils.NetworkResults
import com.example.bsnanny.models.authentication.AuthenticationBody
import com.example.bsnanny.models.authentication.AuthenticationResponse
import com.example.bsnanny.models.checkUser.CheckUserBody
import com.example.bsnanny.models.checkUser.CheckUserResponse
import com.example.bsnanny.utils.safeApiCall
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : ApiHelper {
    override suspend fun checkUser(checkUserBody: CheckUserBody): NetworkResults<CheckUserResponse> {

        return safeApiCall { apiInterface.checkUser(checkUserBody) }
    }

    override suspend fun authenticate(authenticationBody: AuthenticationBody): NetworkResults<AuthenticationResponse> {
        return safeApiCall {
            apiInterface.authenticate(authenticationBody)
        }
    }
}