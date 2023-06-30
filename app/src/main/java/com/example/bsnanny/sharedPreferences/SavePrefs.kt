package com.example.bsnanny.sharedPreferences

import android.content.Context

object SavePrefs {
    fun saveOnBoardingStatus(
        context: Context,
        sharedPrefsName: String,
        sharedPrefKey: String,
        value: Boolean
    ) {
        val sharedPreferences = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            editor.putBoolean(sharedPrefKey, value)
        }.apply()

    }
    fun saveChooseProfileStatus(
        context: Context,
        sharedPrefsName: String,
        sharedPrefKey: String,
        value: String
    ) {
        val sharedPreferences = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            editor.putString(sharedPrefKey, value)
        }.apply()
    }
}