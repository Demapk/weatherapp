package ru.demapk.weatherapp.db.entity.relation

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize
import ru.demapk.weatherapp.db.entity.CurrentEntity
import ru.demapk.weatherapp.db.entity.ForecastEntity
import ru.demapk.weatherapp.db.entity.SavedCityEntity

/**
 * Project: weatherapp
 */
@Parcelize
data class CityWithCurrentWeatherAndForecast(

    @Embedded
    val city: SavedCityEntity,

    @Relation(parentColumn = "id", entityColumn = "cityId")
    val currentWeather: List<CurrentEntity>,

    @Relation(parentColumn = "id", entityColumn = "cityId")
    val forecast: List<ForecastEntity>

): Parcelable