package com.spdigital.weatherapp.roomapi

import androidx.room.Entity

@Entity(primaryKeys = arrayOf("name"))
data class City (
    var id: Long = 0,
    val name: String
)