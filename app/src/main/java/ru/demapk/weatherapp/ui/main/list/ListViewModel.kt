package ru.demapk.weatherapp.ui.main.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ru.demapk.weatherapp.App
import ru.demapk.weatherapp.db.entity.relation.CityWithCurrentWeather

class ListViewModel(private val handle: SavedStateHandle) : ViewModel() {

    private val _citiesList = handle.getLiveData<List<CityWithCurrentWeather>>("citiesList")
    val citiesList: LiveData<List<CityWithCurrentWeather>>
        get() = _citiesList


   fun fetch(){
       _citiesList.postValue(App.db.dao().getCitiesWithWeather())
   }

}