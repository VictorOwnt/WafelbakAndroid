package be.scoutswondelgem.wafelbak.database.utils

import androidx.room.TypeConverter
import be.scoutswondelgem.wafelbak.models.enums.DeliveryStatus

class DeliveryStatusConverter {
    @TypeConverter
    fun deliveryStatusToDatabase(deliveryStatus: DeliveryStatus): String {
        return deliveryStatus.status
    }

    @TypeConverter
    fun fromDatabaseToDeliveryStatus(value: String?): DeliveryStatus? {
        when(value) {
            "Te Bezorgen" -> return DeliveryStatus.NOTDELIVERED
            "Bezorgd" -> return DeliveryStatus.DELIVERED
            else -> return DeliveryStatus.NOTDELIVERED
        }
    }
}