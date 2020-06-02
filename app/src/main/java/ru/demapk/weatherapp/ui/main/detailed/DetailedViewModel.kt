package ru.demapk.weatherapp.ui.main.detailed

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.EMPTY_RESPONSE
import ru.demapk.weatherapp.APPID
import ru.demapk.weatherapp.App
import ru.demapk.weatherapp.LANG
import ru.demapk.weatherapp.UNITS
import ru.demapk.weatherapp.db.entity.CurrentEntity
import ru.demapk.weatherapp.db.entity.ForecastEntity
import ru.demapk.weatherapp.db.entity.SavedCityEntity
import ru.demapk.weatherapp.db.entity.relation.CityWithCurrentWeatherAndForecast

/**
 * Project: weatherapp
 */
class DetailedViewModel(private val handle: SavedStateHandle) : ViewModel() {


    private val _cityId = handle.getLiveData<Long>("cityId")
    val cityId: LiveData<Long>
        get() = _cityId

    private val _weather = handle.getLiveData<CityWithCurrentWeatherAndForecast>("weather")
    val weather: LiveData<CityWithCurrentWeatherAndForecast>
        get() = _weather

    private val _buttonState = handle.getLiveData<Boolean>("buttonState", true)
    val buttonState: LiveData<Boolean>
        get() = _buttonState

    fun init(cityId: Long) {


        _weather.postValue(App.db.dao().getCityFull(cityId))
        fetchData(cityId)

    }


    private fun fetchData(id: Long) {

        viewModelScope.launch(Dispatchers.IO) {

            val current = App.api.getCurrentById(
                id = id,
                lang = LANG,
                units = UNITS,
                appid = APPID
            ).body()

            val forecast = App.api.getHourForecastByCityId(
                id = id,
                lang = LANG,
                units = UNITS,
                appid = APPID
            ).body()

            val db = App.db.dao()

            db.deleteForecast(db.getAllForecastByCity(id))

            if (current != null)
                db.insert(CurrentEntity(current))

            forecast?.list?.forEach {
                db.insert(ForecastEntity(it, id))
            }


            val dbResult = db.getCityFull(id)
            _weather.postValue(dbResult)

        }

    }

    fun buttonAction() {
        if (buttonState.value == true) {

            _weather.value?.city?.let {
                App.db.dao().deleteCity(it)
            }
            _buttonState.postValue(false)

        }
    }

}