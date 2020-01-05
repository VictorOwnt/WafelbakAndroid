package be.scoutswondelgem.wafelbak.adapters

import be.scoutswondelgem.wafelbak.models.DeliveryStatus
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
            "Te Bezorgen" -> return DeliveryStatus.NIETGELEVERD
            "Bezorgd" -> return DeliveryStatus.WELGELEVERD
            else -> return DeliveryStatus.NIETGELEVERD
        }
    }
}