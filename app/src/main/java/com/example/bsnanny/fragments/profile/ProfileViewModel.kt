package com.example.bsnanny.fragments.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    val genderSelect: MutableLiveData<String> = MutableLiveData("Boy")
    val editMode: MutableLiveData<Boolean> = MutableLiveData(false)
}