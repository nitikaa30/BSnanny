package com.example.bsnanny.views.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _genderSelect = MutableLiveData<String>()
    val genderSelect: LiveData<String> = _genderSelect

    private val _isProfileEditable = MutableLiveData<Boolean>()
    val isProfileEditable: LiveData<Boolean> = _isProfileEditable

    private val _isProfileVisible = MutableLiveData<Boolean>()
    val isProfileVisible: LiveData<Boolean> = _isProfileVisible

    fun setGenderSelect(gender: String) {
        _genderSelect.value = gender
    }

    fun setProfileEditable(editable: Boolean) {
        _isProfileEditable.value = editable
    }

    fun setProfileVisible(visible: Boolean) {
        _isProfileVisible.value = visible
    }
}