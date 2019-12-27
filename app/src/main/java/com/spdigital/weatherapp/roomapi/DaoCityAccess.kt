package com.spdigital.weatherapp.roomapi

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DaoCityAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(city: City): Long?


/* @Query("SELECT * FROM City ORDER BY updateTime desc")
LiveData<List<City>> fetchAllTasks();*/

    @Query("SELECT * FROM City ORDER BY id desc")
    fun fetchAllCity(): LiveData<List<City>>

    @Query("SELECT * FROM City ORDER BY id desc LIMIT :limit")
    fun fetchCityNameList(limit:Int): LiveData<List<City>>

    @Query("SELECT * FROM City WHERE id =:cityId")
    fun getTask(cityId: Int): LiveData<City>


    @Update
    fun updateTask(city: City)


    @Delete
    fun deleteTask(city: City)
}