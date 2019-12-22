package be.scoutswondelgem.wafelbak.util

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromDatabase(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToDatabase(date: Date?): Long? {
        return date?.time?.toLong()
    }
}