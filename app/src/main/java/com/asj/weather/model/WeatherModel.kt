package com.asj.weather.model

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class WeatherModel {
    @SerializedName("location")
    var location: Location? = null

    @SerializedName("current")
    var currentTemp: CurrentTemp? = null

    @SerializedName("forecast")
    var foreCastTemp: ForeCastTemp? = null

    inner class Location {
        @SerializedName("name")
        var name: String? = null
    }

    inner class CurrentTemp {
        @SerializedName("temp_c")
        var tempC: Double = 0.toDouble()
    }

    inner class ForeCastTemp {
        @SerializedName("forecastday")
        var alForeCastDay: ArrayList<ForeCastDay>? = null
    }

    inner class ForeCastDay {
        @SerializedName("date")
        internal var date: String? = null

        @SerializedName("day")
        internal var dayOfForeCastDay: DayOfForeCastDay? = null
    }

    internal inner class DayOfForeCastDay {
        @SerializedName("avgtemp_c")
        var avgTempC: Double = 0.toDouble()
    }
}
