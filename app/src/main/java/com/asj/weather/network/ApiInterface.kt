package com.asj.weather.network

import com.asj.weather.model.WeatherModel

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("forecast.json?")
    fun getWeatherInfo(@Query("key") key: String, @Query("q") location: String, @Query("days") days: String): Call<WeatherModel>
}
