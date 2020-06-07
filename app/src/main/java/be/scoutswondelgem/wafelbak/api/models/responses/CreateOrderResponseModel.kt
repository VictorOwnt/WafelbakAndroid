package be.scoutswondelgem.wafelbak.api.models.responses

import be.scoutswondelgem.wafelbak.api.models.enums.AmountOfWaffles
import be.scoutswondelgem.wafelbak.api.models.enums.DeliveryStatus
import be.scoutswondelgem.wafelbak.api.models.enums.DeliveryTime
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateOrderResponseModel(
    @field:Json(name = "id")
    var orderId: Int,
    @field:Json(name = "name")
    var name: String,
    @field:Json(name = "amountOfWaffles")
    var amountOfWaffles: AmountOfWaffles,
    @field:Json(name = "desiredOrderTime")
    var desiredOrderTime: DeliveryTime,
    @field:Json(name = "status")
    var status: DeliveryStatus,
    @field:Json(name = "comment")
    var comment: String?,
    @field:Json(name = "AddressId")
    var addressId: Int
)