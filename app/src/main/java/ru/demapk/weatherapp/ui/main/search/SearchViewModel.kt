package ru.demapk.weatherapp.ui.main.search

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.demapk.weatherapp.*
import ru.demapk.weatherapp.db.entity.CurrentEntity
import ru.demapk.weatherapp.db.entity.SavedCityEntity
import ru.demapk.weatherapp.network.model.Current

class SearchViewModel(handle: SavedStateHandle) : ViewModel() {

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

    fun search(query: String) {
        if (query.length > 2) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                _loading.postValue(true)
                delay(400)
                val response = request {
                    App.api.getCurrentByString(
                        city = query,
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

}