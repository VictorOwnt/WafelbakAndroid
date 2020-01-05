package be.scoutswondelgem.wafelbak.models

import com.squareup.moshi.Json

data class OrderAndUser(
    @field:Json(name="id")
    var orderId: Int,
    @field:Json(name="amountOfWaffles")
    var amountOfWaffles: Int,
    @field:Json(name="desiredDeliveryTime")
    var desiredDeliveryTime: DeliveryDate,
    @field:Json(name="comment")
    var comment: String?,
    @field:Json(name="UserId")
    var userId: Int,
    @field:Json(name="status")
    var deliveryStatus: DeliveryStatus = DeliveryStatus.NIETGELEVERD,
    @field:Json(name="User")
    var user: User
)