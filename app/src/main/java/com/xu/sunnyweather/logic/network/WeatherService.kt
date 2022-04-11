package com.xu.sunnyweather.logic.network

import com.xu.sunnyweather.SunnyWeatherApplication
import com.xu.sunnyweather.logic.model.DailyResponse
import com.xu.sunnyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String):
            Call<RealtimeResponse>

    //"lat":39.865246,"lng":116.378517}
    //v2.5/oJsBvdVhRJ8qKupc/116.378517,39.865246/realtime.json
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String):
            Call<DailyResponse>
}