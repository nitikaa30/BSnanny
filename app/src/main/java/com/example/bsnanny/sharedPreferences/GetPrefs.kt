package com.example.bsnanny.sharedPreferences

import android.content.Context

object GetPrefs {
    fun getOnBoardingStatus(context: Context, sharedPrefsName: String, sharedPrefKey: String) : Boolean{
        val sharedPreferences = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(sharedPrefKey, false)
    }
    fun getChooseProfileStatus(context: Context, sharedPrefsName: String, sharedPrefKey: String) : String?{
        val sharedPreferences = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(sharedPrefKey, null)
    }

}