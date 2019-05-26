package com.asj.weather.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.asj.weather.model.WeatherModel
import com.asj.weather.network.ApiInterface
import com.asj.weather.network.ApiResponse
import com.asj.weather.util.Constants
import com.asj.weather.util.Utility

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    fun getWeatherInfo(apiInterface: ApiInterface?, application: Application, wiLiveData: MutableLiveData<ApiResponse<WeatherModel>>, latitude: Double, longitude: Double) {
        if (apiInterface != null && Utility.isInternetConnected(application) && latitude != 0.0 && longitude != 0.0) {
            val call = apiInterface.getWeatherInfo(Constants.API_KEY, "$latitude,$longitude", Constants.FORECAST_DAYS)
            call.enqueue(object : Callback<WeatherModel> {
                override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>?) {
                    if (response?.body() != null) {
                        val weatherModel = response.body()
                        wiLiveData.postValue(ApiResponse.success(weatherModel))
                    } else {
                        wiLiveData.postValue(ApiResponse.failure())
                    }
                }

                override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                    wiLiveData.postValue(ApiResponse.failure())
                }
            })
        } else {
            wiLiveData.postValue(ApiResponse.failure())
        }
    }

    companion object {

        private var sInstance: WeatherRepository? = null

        val repository: WeatherRepository?
            get() {
                if (sInstance == null) {
                    synchronized(WeatherRepository::class.java) {
                        if (sInstance == null) {
                            sInstance = WeatherRepository()
                        }
                    }
                }

                return sInstance
            }
    }
}
