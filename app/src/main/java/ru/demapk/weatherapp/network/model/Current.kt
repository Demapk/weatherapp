package ru.demapk.weatherapp.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Date: 2020-04-19
 * Project: weatherapp
 */
@Parcelize
data class Current (
    var weather: List<Weather>? = null,
    var main: CurrentMain? = null,
    var wind: Wind? = null,
    var dt: Long = 0,
    var name: String = "",
    var id: Long
): Parcelable