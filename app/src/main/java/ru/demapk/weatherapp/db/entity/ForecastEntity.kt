package ru.demapk.weatherapp.db.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import ru.demapk.weatherapp.network.model.Forecast3HourItem
import ru.demapk.weatherapp.network.model.Forecast3HourMain
import ru.demapk.weatherapp.network.model.Weather
import ru.demapk.weatherapp.network.model.Wind

/**
 * Project: weatherapp
 */
@Parcelize
@Entity
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val dt: Long,
    val cityId: Long,
    @Embedded
    val weather: Weather?,
    @Embedded
    val main: Forecast3HourMain?,
    @Embedded
    val wind: Wind?,
    val textDate: String? = null
) : Parcelable {


    constructor(forecast3HourItem: Forecast3HourItem, cityId: Long) : this(
        0,
        forecast3HourItem.dt,
        cityId,
        forecast3HourItem.weather?.get(0),
        forecast3HourItem.main,
        forecast3HourItem.wind,
        forecast3HourItem.textDate
    )


}