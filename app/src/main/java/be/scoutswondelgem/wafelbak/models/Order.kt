package be.scoutswondelgem.wafelbak.models

import java.util.*

data class Order (
    val orderId: Long,
    val amountOfWaffles: Int,
    val dateOrdered: Date,
    val desiredDeliveryTime: DeliveryDate,
    val comment: String?,
    val userId: Long
)