package be.scoutswondelgem.wafelbak.util

import androidx.room.TypeConverter
import be.scoutswondelgem.wafelbak.models.DeliveryDate
import be.scoutswondelgem.wafelbak.models.DeliveryStatus
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
            "Het maakt niet uit" -> return DeliveryDate.MAAKTNIETUIT
            else -> return DeliveryDate.MAAKTNIETUIT
        }
    }

    @TypeConverter
    fun delieryStatusToDatabase(deliveryStatus: DeliveryStatus): String {
        return deliveryStatus.status
    }

    @TypeConverter
    fun fromDatabaseToDeliveryStatus(value: String?): DeliveryStatus? {
        when(value) {
            "Te Bezorgen" -> return DeliveryStatus.NIETGELEVERD
            "Bezorgd" -> return DeliveryStatus.WELGELEVERD
            else -> return DeliveryStatus.NIETGELEVERD
        }
    }

}