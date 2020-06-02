package ru.demapk.weatherapp.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import ru.demapk.weatherapp.network.model.Current

/**
 * Date: 2020-04-19
 * Project: weatherapp
 */
@Parcelize
@Entity
data class SavedCityEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
) : Parcelable {

    constructor(current: Current) : this(current.id, current.name)

}