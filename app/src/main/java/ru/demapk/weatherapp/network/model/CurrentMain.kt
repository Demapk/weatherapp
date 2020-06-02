package ru.demapk.weatherapp.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentMain(
    var temp: Double = 0.0,
    var pressure: Int = 0,
    var humidity: Int = 0,
    @SerializedName("temp_min")
    var tempMin: Double = 0.0,
    @SerializedName("temp_max")
    var tempMax: Double = 0.0
): Parcelable