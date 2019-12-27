package com.spdigital.weatherapp.roomapi

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class CityDB : RoomDatabase() {
    abstract fun daoBookAccess(): DaoCityAccess
}
