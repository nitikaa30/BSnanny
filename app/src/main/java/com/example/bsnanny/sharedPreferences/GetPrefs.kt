package com.example.babysitting.sharedPreferences

import android.content.Context

object GetPrefs {
    fun getOnBoardingStatus(context: Context, sharedPrefsName: String, sharedPrefKey: String) : Boolean{
        val sharedPreferences = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(sharedPrefKey, false)
    }
}