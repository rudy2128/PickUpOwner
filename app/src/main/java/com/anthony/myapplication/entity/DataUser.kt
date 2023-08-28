package com.anthony.myapplication.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

data class DataUser(
    val date:String,
    val long:Double,
    val lat:Double
)
