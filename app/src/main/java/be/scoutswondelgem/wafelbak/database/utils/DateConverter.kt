package be.scoutswondelgem.wafelbak.database.utils

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    @TypeConverter
    fun dateToDatabase(date: Date): String {
        return dateFormat.format(date)
    }

    @TypeConverter
    fun fromDatabaseToDate(value: String): Date? {
        return if (value == null) null else dateFormat.parse(value)
    }
}