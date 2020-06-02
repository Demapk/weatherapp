package ru.demapk.weatherapp.network.model

/**
 * Date: 2020-04-19
 * Project: weatherapp
 */
data class ForecastHourly(
    var list: List<Forecast3HourItem> = emptyList(),
    var city: City? = null
)