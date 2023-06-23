package com.example.babysitting.sharedPreferences

import android.content.Context

object SavePrefs {
    fun saveOnBoardingStatus(context: Context, sharedPrefsName: String, sharedPrefKey: String, value : Boolean){
        val sharedPreferences = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            editor.putBoolean(sharedPrefKey, value)
        }.apply()

    }
}