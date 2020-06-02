package ru.demapk.weatherapp.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.demapk.weatherapp.network.model.Current
import ru.demapk.weatherapp.network.model.ForecastHourly

/**
 * Date: 2020-04-19
 * Project: weatherapp
 */
interface Api {

    @GET("weather")
    suspend fun getCurrentByString(
        @Query("q") city: String, @Query("lang") lang: String,
        @Query("units") units: String, @Query("APPID") appid: String
    ): Response<Current>

    @GET("weather")
    suspend fun getCurrentByGeo(
        @Query("lat") lat: String, @Query("lon") lon: String, @Query("lang") lang: String,
        @Query("units") units: String, @Query("APPID") appid: String
    ): Response<Current>

    @GET("weather")
    suspend fun getCurrentById(
        @Query("id") id: Long, @Query("lang") lang: String,
        @Query("units") units: String, @Query("APPID") appid: String
    ): Response<Current>


    @GET("forecast")
    suspend fun getHourForecastByCityId(
        @Query("id") id: Long, @Query("lang") lang: String,
        @Query("units") units: String, @Query("APPID") appid: String
    ): Response<ForecastHourly>

}

