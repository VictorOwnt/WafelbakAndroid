package be.scoutswondelgem.wafelbak.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(
    @field:Json(name = "id")
    var addressId: Int,
    @field:Json(name = "streetNumber")
    var streetNumber: Int,
    @field:Json(name = "streetExtra")
    var streetExtra: String?,
    @field:Json(name = "Street")
    var street: Street
)