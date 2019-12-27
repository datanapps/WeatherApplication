package com.spdigital.weatherapp.di.module

import com.spdigital.weatherapp.views.cityweather.CityWeatherActivity
import com.spdigital.weatherapp.views.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun homeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun cityWeatherActivity(): CityWeatherActivity
}