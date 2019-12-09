package be.scoutswondelgem.wafelbak.models

import java.util.*

data class Order (
    val id: Int,
    val amountOfWaffles: Int,
    val dateOrdered: Date,
    val desiredDeliveryTime: String
)