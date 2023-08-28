package com.anthony.myapplication.helper

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    val currentDate: String
        get() {
            val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val date = Date()
            return dateFormat.format(date)
        }

}