package be.scoutswondelgem.wafelbak.adapters

import be.scoutswondelgem.wafelbak.models.DeliveryDate
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class DeliveryTimeAdapter {
    @ToJson
    internal fun delieryDateToJson(deliveryDate: DeliveryDate): String {
        return deliveryDate.levertijd
    }

    @FromJson
    internal fun fromJson(json: String): DeliveryDate? {
        when(json) {
            "Voor 12u" -> return DeliveryDate.AVOND
            "Na 16u" -> return DeliveryDate.VOORMIDDAG
            "Tussen 13u en 16u" -> return DeliveryDate.NAMIDDAG
            else -> return null
        }
    }
}