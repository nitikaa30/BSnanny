package com.example.bsnanny.authUser

import com.example.bsnanny.models.authentication.AuthenticationData
import com.example.bsnanny.utils.sharedPreferences.SharedPreferences


class AuthUser {
    fun saveToken(
        sharedPrefKey: String,
        authenticationData: AuthenticationData?
    ) {
        SharedPreferences.saveUser(sharedPrefKey, authenticationData!!)
    }

    fun getUser(
    ): AuthenticationData? {
        return SharedPreferences.getUser(SharedPreferences.SAVE_JWT_USER_KEY)
    }
}