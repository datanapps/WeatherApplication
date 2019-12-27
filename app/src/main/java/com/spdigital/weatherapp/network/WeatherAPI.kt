package com.spdigital.weatherapp.network

import com.spdigital.weatherapp.network.model.BaseWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherAPI{
    /**
     * Get the list of the pots from the API
     */
    // http://api.worldweatheronline.com/premium/v1/weather.ashx?key=20a1ba28fc244d2786875745191811&q=London&format=json&num_of_days=1

    @GET("premium/v1/weather.ashx")
    fun getWeatherDetail(@Query("key") key:String, @Query("q")  q : String, @Query("format")  format: String, @Query("num_of_days")  num_of_days: String): Call<BaseWeather>

}
