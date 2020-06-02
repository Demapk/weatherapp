package ru.demapk.weatherapp.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Wind(var speed: Double = 0.0, var deg: Double = 0.0) : Parcelable {

}
