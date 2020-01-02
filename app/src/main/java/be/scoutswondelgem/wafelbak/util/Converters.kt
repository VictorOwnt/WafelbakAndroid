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
        return deliveryDate.levertijd
    }

    @TypeConverter
    fun fromDatabase(value: String?): DeliveryDate? {
        when(value) {
            "Voor 12u" -> return DeliveryDate.AVOND
            "Na 16u" -> return DeliveryDate.VOORMIDDAG
            "Tussen 13u en 16u" -> return DeliveryDate.NAMIDDAG
            else -> return null
        }
    }

}