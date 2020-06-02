package ru.demapk.weatherapp.ui.main.geo

import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import ru.demapk.weatherapp.*
import ru.demapk.weatherapp.db.entity.CurrentEntity
import ru.demapk.weatherapp.db.entity.SavedCityEntity
import ru.demapk.weatherapp.network.model.Current

class GeoViewModel(private val handle: SavedStateHandle) : ViewModel() {

    private val _lastGeo = handle.getLiveData<Location>("lastGeo")
    val lastGeo: LiveData<Location>
        get() = _lastGeo

    private val _found = handle.getLiveData<Current?>("found")
    val found: LiveData<Current?>
        get() = _found

    private val _buttonState = handle.getLiveData<Boolean>("buttonState", false)
    val buttonState: LiveData<Boolean>
        get() = _buttonState

    private val _loading = handle.getLiveData<Boolean>("loading")
    val loading: LiveData<Boolean>
        get() = _loading

    var searchJob: Job? = null


    fun buttonAction() {
        val foundWeather = _found.value
        if (buttonState.value == true) {

            //Like if not null
            foundWeather?.let {

                App.db.dao().deleteCity(SavedCityEntity(it))
                _buttonState.postValue(false)

            }
        } else {

            //Like if not null
            foundWeather?.let {

                val city = SavedCityEntity(it)
                val weather = CurrentEntity(it)


                val insertedCity = App.db.dao().insert(city)
                val insertedWeather = App.db.dao().insert(weather)

                Log.e("room", "city: $insertedCity, weather: $insertedWeather")

                _buttonState.postValue(true)
            }
        }
    }

    fun fetchWithLocation(location: Location) {

        if (_lastGeo.value?.latitude != location.latitude || _lastGeo.value?.longitude != location.longitude) {
            _lastGeo.postValue(location)

            searchJob?.cancel()
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                _loading.postValue(true)
                delay(400)
                val response = request {
                    App.api.getCurrentByGeo(
                        lat = location.latitude.toString(),
                        lon = location.longitude.toString(),
                        lang = LANG,
                        units = UNITS,
                        appid = APPID
                    )
                }
                _loading.postValue(false)


                response?.let {
                    App.db.dao().insert(CurrentEntity(response))
                }
                _found.postValue(response)

                val cities = App.db.dao().getCities()
                val isSaved =
                    cities.findLast { it.id == response?.id } != null
                Log.e("room", cities.toString())
                _buttonState.postValue(isSaved)

            }
        }


    }

}