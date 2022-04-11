package com.xu.sunnyweather

import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {

    companion object {
        @SuppressWarnings("StaticFieldLeak")
        lateinit var context: Context

        //天气APIToken
        const val TOKEN = "oJsBvdVhRJ8qKupc"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}