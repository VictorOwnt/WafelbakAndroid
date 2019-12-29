package be.scoutswondelgem.wafelbak.util

import androidx.room.TypeConverter
import be.scoutswondelgem.wafelbak.models.DeliveryDate
import java.util.*

class Converters {
    @TypeConverter
    fun fromDatabase(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToDatabase(date: Date): Long {
        return date.time.toLong()
    }

    @TypeConverter
    fun deliveryDateToDatabase(deliveryDate: DeliveryDate): String {
        return deliveryDate.toString()
    }

    @TypeConverter
    fun fromDatabase(value: String?): DeliveryDate? {
        when(value) {
            DeliveryDate.AVOND.levertijd -> return DeliveryDate.AVOND
            DeliveryDate.VOORMIDDAG.levertijd -> return DeliveryDate.VOORMIDDAG
            DeliveryDate.NAMIDDAG.levertijd -> return DeliveryDate.NAMIDDAG
            else -> return null
        }
    }

}