package ru.varasoft.pictureoftheday.util

import java.text.SimpleDateFormat
import java.util.*

object Util {

    fun getDateRelativeToToday(offset: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        return sdf.format(calendar.getTime())
    }
}