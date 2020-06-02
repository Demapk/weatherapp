package ru.demapk.weatherapp.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    @SerializedName("id")
    var weatherId: Long = 0,
    @SerializedName("main")
    var weatherMain: String? = "",
    var description: String? = "",
    var icon: String? = ""
): Parcelable
