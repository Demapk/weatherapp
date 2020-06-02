package ru.demapk.weatherapp

import android.app.Application
import android.os.Looper
import android.util.Log
import androidx.room.Room
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.demapk.weatherapp.db.AppDatabase
import ru.demapk.weatherapp.network.Api

/**
 * Date: 2020-04-19
 * Project: weatherapp
 */

const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
const val LANG = "ru"
const val UNITS = "metric"
const val APPID = "1b9e76fe25d957f861a72ddae1020f26"

class App : Application() {


    companion object {

        lateinit var db: AppDatabase

        lateinit var api: Api




    }

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(this, AppDatabase::class.java, "weather")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor).build()
            )
            .build()

        api = retrofit.create(Api::class.java)

    }


}