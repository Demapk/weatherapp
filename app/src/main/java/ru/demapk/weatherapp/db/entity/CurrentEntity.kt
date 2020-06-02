package ru.demapk.weatherapp.db.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import ru.demapk.weatherapp.network.model.Current
import ru.demapk.weatherapp.network.model.CurrentMain
import ru.demapk.weatherapp.network.model.Weather
import ru.demapk.weatherapp.network.model.Wind

/**
 * Project: weatherapp
 */
@Parcelize
@Entity
data class CurrentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val cityId: Long,
    @Embedded
    val weather: Weather?,
    @Embedded
    val main: CurrentMain?,
    @Embedded
    val wind: Wind?,
    val dt: Long
): Parcelable {


    constructor(current: Current) : this(
        0L,
        current.id,
        current.weather?.get(0),
        current.main,
        current.wind,
        current.dt
    )

}