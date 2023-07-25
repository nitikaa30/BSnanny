package com.example.bsnanny.models.findNanny.inviteNanny

import com.google.gson.annotations.SerializedName

data class InviteNannyBody(
    @SerializedName("nany_id")
    val nannyId : String
)
