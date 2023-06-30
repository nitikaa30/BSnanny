package com.example.bsnanny

sealed class NetworkResults<T>(val data : T ?= null, val errorMessage : String ?= null)
{
    class Loading<T>:NetworkResults<T>()
    class Success<T>(data: T?= null) :NetworkResults<T>(data = data)
    class Error<T>(errorMessage: String?) :NetworkResults<T>(errorMessage = errorMessage)
}