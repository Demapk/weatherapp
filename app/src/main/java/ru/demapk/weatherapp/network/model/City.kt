package ru.demapk.weatherapp.network.model

data class City (
    var id: Long = 0,
    var name: String = "",
    var coord: Coord? = null,
    var country: String = ""
)
