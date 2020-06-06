package be.scoutswondelgem.wafelbak.api.adapters

import be.scoutswondelgem.wafelbak.models.enums.DeliveryTime
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class DeliveryTimeAdapter {
    @ToJson
    internal fun deliveryDateToJson(deliveryTime: DeliveryTime): String {
        return deliveryTime.deliveryTime
    }

    @FromJson
    internal fun fromJson(json: String): DeliveryTime? {
        when(json) {
            "9u-12u" -> return DeliveryTime.MORNING
            "13u-16u" -> return DeliveryTime.AFTERNOON
            "16u-18u" -> return DeliveryTime.EVENING
            "Om het even" -> return DeliveryTime.DOESNTMATTER
            else -> return DeliveryTime.DOESNTMATTER
        }
    }
}