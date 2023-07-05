package com.example.bsnanny.models.payment

sealed class PaymentModel {
    class CardModel(
        val cardPhoto : Int,
        val cardName : String
    ) : PaymentModel()

    class AddNewCard(
        val add : String,
        val photo : Int
    ) : PaymentModel()
}