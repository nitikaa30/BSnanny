package com.example.bsnanny.utils.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.bsnanny.models.authentication.AuthenticationData
import com.google.gson.Gson

object SharedPreferences {

    private const val SHARED_PREF = "com.example.bsnanny.sharedpref"
    const val AUTH_KEY = "AUTH_KEY"
    const val ONBOARDING_KEY = "ONBOARDING_KEY"
    const val SAVE_JWT_USER_KEY = "SAVE_JWT_TOKEN_KEY"
    private lateinit var sharedPreferences: SharedPreferences

    fun initSharedPref(context: Context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }
    fun saveOnBoardingStatus(
        sharedPrefKey: String,
        value: Boolean
    ) {
        val editor = sharedPreferences.edit()
        editor.apply {
            editor.putBoolean(sharedPrefKey, value)
        }.apply()
    }
    fun getOnBoardingStatus(sharedPrefKey: String) : Boolean{
        return sharedPreferences.getBoolean(sharedPrefKey, false)
    }

    fun saveChooseProfileStatus(
        sharedPrefKey: String,
        value: String
    ) {
        val editor = sharedPreferences.edit()
        editor.apply {
            editor.putString(sharedPrefKey, value)
        }.apply()
    }

    fun saveUser(
        sharedPrefKey: String,
        authenticationData: AuthenticationData
    ) {
        val gson = Gson()
        val jsonObject : String = gson.toJson(authenticationData)
        val editor = sharedPreferences.edit()
        editor.apply {
            editor.putString(sharedPrefKey, jsonObject)
        }.apply()
    }
    fun getUser(key : String) : AuthenticationData?{
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        return gson.fromJson(json, AuthenticationData::class.java) ?: null
    }
    fun getChooseProfileStatus(sharedPrefKey: String) : String?{
        return sharedPreferences.getString(sharedPrefKey, null)
    }
}