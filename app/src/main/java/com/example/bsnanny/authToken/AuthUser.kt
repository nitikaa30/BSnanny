package com.example.bsnanny.authToken

import com.example.bsnanny.models.authentication.AuthenticationData
import com.example.bsnanny.utils.sharedPreferences.SharedPreferences


class AuthUser {
    fun saveToken(
        sharedPrefKey: String,
        authenticationData: AuthenticationData?
    ) {
        SharedPreferences.saveUser(sharedPrefKey, authenticationData!!)
    }
}