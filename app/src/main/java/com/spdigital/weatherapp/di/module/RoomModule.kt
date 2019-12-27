package com.spdigital.weatherapp.di.module

import android.content.Context
import androidx.room.Room
import com.spdigital.weatherapp.roomapi.CityDB
import com.spdigital.weatherapp.utils.Constants

import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton





@Module
class RoomModule @Inject constructor(){

    private var context: Context? = null
    private var appDatabase: CityDB?=null

    fun init(context: Context) {
        this.context = context

    }

    @Provides
    @Singleton
    fun getAppDatabase(): CityDB {
        if(appDatabase==null){
            appDatabase = Room.databaseBuilder(this.context!!, CityDB::class.java, Constants.DB_WEATHER).build()
        }

        return this.appDatabase!!
    }

}
