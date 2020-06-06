package be.scoutswondelgem.wafelbak.api.adapters

import be.scoutswondelgem.wafelbak.models.enums.DeliveryStatus
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class DeliveryStatusAdapter {
    @ToJson
    internal fun deliveryStatusToJson(deliveryStatus: DeliveryStatus): String {
        return deliveryStatus.status
    }

    @FromJson
    internal fun fromJson(json: String): DeliveryStatus? {
        when(json) {
            "Te Bezorgen" -> return DeliveryStatus.NOTDELIVERED
            "Bezorgd" -> return DeliveryStatus.DELIVERED
            else -> return DeliveryStatus.NOTDELIVERED
        }
    }
}