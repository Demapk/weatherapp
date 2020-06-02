package ru.demapk.weatherapp

import retrofit2.Response

/**
 * Created by antonsarmatin
 * Date: 2020-04-19
 * Project: weatherapp
 */

suspend fun <T> request(call: suspend () -> Response<T>): T? {
    return try {
        call.invoke().body()
    } catch (e: Exception) {
        null
    }
}