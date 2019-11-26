package be.scoutswondelgem.wafelbak.util

import androidx.room.*
import java.util.*


class Converters {
    @TypeConverter
    fun toDate(dateLong:Long): Date {
        return Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date):Long{
        return date.time
    }
}