package ru.demapk.weatherapp.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast3HourMain (var temp: Double = 0.0,
    var temp_min: Double = 0.0,
    var temp_max: Double = 0.0,
    var pressure: Double = 0.0,
    var humidity: Int = 0,
    var temp_kf: Double = 0.0): Parcelable