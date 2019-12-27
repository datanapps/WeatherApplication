package com.spdigital.weatherapp.di.component

import com.spdigital.weatherapp.di.module.ActivitiesModule
import com.spdigital.weatherapp.di.module.NetworkModule
import com.spdigital.weatherapp.di.module.RoomModule
import com.spdigital.weatherapp.di.module.UtilsModule
import com.spdigital.weatherapp.network.WeatherAPI
import com.spdigital.weatherapp.roomapi.CityDB
import com.spdigital.weatherapp.views.WeatherApplication
import com.spdigital.weatherapp.views.cityweather.CityWeatherActivity
import com.spdigital.weatherapp.views.home.HomeActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import retrofit2.Retrofit
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidInjectionModule::class, NetworkModule::class, UtilsModule::class, ActivitiesModule::class, RoomModule::class])

interface ApplicationComponent : AndroidInjector<WeatherApplication> {

    override fun inject(instance: WeatherApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: WeatherApplication): Builder
        fun build(): ApplicationComponent
    }

    // activities
    fun inject(homeActivity: HomeActivity)
    fun inject(cityWeatherActivity: CityWeatherActivity)

    // network module
    fun exposeAppPref(): WeatherAPI
    fun exposeApp(): Retrofit


    // utils
    fun isOnlineCheck(): Boolean

    // room api
    fun getAppDatabase(): CityDB


}
