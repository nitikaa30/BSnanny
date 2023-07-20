package com.example.bsnanny.models.findNanny

import android.os.Parcel
import android.os.Parcelable


data class FindNannyApiItems(
    val latitude: Double? = null,
    val longitude: Double? = null,
    val price: String?= null,
    val noOfChildren: String ?= null,
    val from: String ?= null,
    val to: String ?= null,
    val startDate: String ?= null,
    val endDate: String ?= null,
    val address: String?= null,
    val city: String?= null,
    val pin: String ?= null,
    val country: String?= null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(latitude)
        parcel.writeValue(longitude)
        parcel.writeString(price)
        parcel.writeString(noOfChildren)
        parcel.writeString(from)
        parcel.writeString(to)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
        parcel.writeString(address)
        parcel.writeString(city)
        parcel.writeString(pin)
        parcel.writeString(country)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FindNannyApiItems> {
        override fun createFromParcel(parcel: Parcel): FindNannyApiItems {
            return FindNannyApiItems(parcel)
        }

        override fun newArray(size: Int): Array<FindNannyApiItems?> {
            return arrayOfNulls(size)
        }
    }
}
