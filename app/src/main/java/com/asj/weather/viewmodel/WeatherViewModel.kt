package com.asj.weather.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.asj.weather.model.WeatherModel
import com.asj.weather.network.ApiInterface
import com.asj.weather.network.ApiResponse

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: WeatherRepository? = WeatherRepository.repository

    var wiLiveData = MutableLiveData<ApiResponse<WeatherModel>>()

    fun updateWeatherInfo(apiInterface: ApiInterface?, latitude: Double, longitude: Double) {
        mRepository?.getWeatherInfo(apiInterface, getApplication(), wiLiveData, latitude, longitude)
    }
}