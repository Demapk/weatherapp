package ru.demapk.weatherapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.demapk.weatherapp.db.entity.CurrentEntity
import ru.demapk.weatherapp.db.entity.ForecastEntity
import ru.demapk.weatherapp.db.entity.SavedCityEntity

/**
 * Date: 2020-04-19
 * Project: weatherapp
 */
@Database(
    entities = [SavedCityEntity::class, CurrentEntity::class, ForecastEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): Dao

}