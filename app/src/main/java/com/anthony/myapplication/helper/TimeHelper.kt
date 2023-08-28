package com.anthony.myapplication.helper

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object TimeHelper {
    val currentDate: String
        get() {
            val dateFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = Date()
            return dateFormat.format(date)
        }
}