package ru.demapk.weatherapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import ru.demapk.weatherapp.db.entity.CurrentEntity
import ru.demapk.weatherapp.db.entity.ForecastEntity
import ru.demapk.weatherapp.db.entity.SavedCityEntity
import ru.demapk.weatherapp.db.entity.relation.CityWithCurrentWeather
import ru.demapk.weatherapp.db.entity.relation.CityWithCurrentWeatherAndForecast

/**
 * Date: 2020-04-19
 * Project: weatherapp
 */
@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityEntity: SavedCityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currentEntity: CurrentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forecastEntity: ForecastEntity)

    @Transaction
    @Query("SELECT * FROM ForecastEntity WHERE cityId = :cityId")
    fun getAllForecastByCity(cityId: Long): List<ForecastEntity>

    @Transaction
    @Query("SELECT * FROM SavedCityEntity")
    fun getCities(): List<SavedCityEntity>

    @Transaction
    @Query("SELECT * FROM SavedCityEntity")
    fun getCitiesWithWeather(): List<CityWithCurrentWeather>

    @Transaction
    @Query("SELECT * FROM savedcityentity")
    fun getCitiesFull(): List<CityWithCurrentWeatherAndForecast>

    @Transaction
    @Query("SELECT * FROM savedcityentity WHERE id = :cityId")
    fun getCityFull(cityId: Long): CityWithCurrentWeatherAndForecast?

    @Delete
    fun deleteCity(savedCityEntity: SavedCityEntity)

    @Delete
    fun deleteForecast(forecasts: List<ForecastEntity>)


}