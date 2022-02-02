package com.abdulrahman.contactlistapp.core

import java.text.SimpleDateFormat
import java.util.*

const val MONTH_DAY_YEAR_SLASH_FORMAT = "dd/MM/yyyy"
object DateUtils {
    fun formatDate(
        dateStr: String?,
        sourceDateFormat: String?,
        destinationDateFormat: String?
    ): String {
        var date = ""
        val sdf = SimpleDateFormat(sourceDateFormat, Locale.ENGLISH)
        val formatter = SimpleDateFormat(destinationDateFormat, Locale.ENGLISH)
        try {
            val parse = sdf.parse(dateStr)
            date = formatter.format(parse)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

}