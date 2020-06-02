package ru.demapk.weatherapp.network.model

import com.google.gson.annotations.SerializedName

data class Forecast3HourItem (
    var dt: Long = 0,
    var main: Forecast3HourMain? = null,
    var weather: List<Weather>? = null,
    var wind: Wind? = null,
    @SerializedName("dt_txt")
    var textDate: String? = null
)
