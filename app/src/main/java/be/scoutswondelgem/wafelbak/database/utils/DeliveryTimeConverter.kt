package be.scoutswondelgem.wafelbak.database.utils

import androidx.room.TypeConverter
import be.scoutswondelgem.wafelbak.models.enums.DeliveryTime

class DeliveryTimeConverter {
    @TypeConverter
    fun deliveryTimeToDatabase(deliveryTime: DeliveryTime): String {
        return deliveryTime.deliveryTime
    }

    @TypeConverter
    fun fromDatabaseToDeliveryTime(value: String): DeliveryTime? {
        when(value) {
            "9u-12u" -> return DeliveryTime.MORNING
            "13u-16u" -> return DeliveryTime.AFTERNOON
            "16u-18u" -> return DeliveryTime.EVENING
            "Om het even" -> return DeliveryTime.DOESNTMATTER
            else -> return DeliveryTime.DOESNTMATTER
        }
    }
}