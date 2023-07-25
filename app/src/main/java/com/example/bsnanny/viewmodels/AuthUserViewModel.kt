package com.example.bsnanny.viewmodels

import androidx.lifecycle.ViewModel
import com.example.bsnanny.authUser.AuthUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// AuthUserViewModel.kt
@HiltViewModel
class AuthUserViewModel @Inject constructor(private val authUser: AuthUser) : ViewModel() {
    fun getAuthUser(): AuthUser {
        return authUser
    }
}
