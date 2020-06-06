package be.scoutswondelgem.wafelbak.api.models

import com.squareup.moshi.Json

data class StreetResponseModel(
    @field:Json(name = "id")
    var streetId: Int,
    @field:Json(name = "streetName")
    var streetName: String,
    @field:Json(name = "City")
    var city: City,
    @field:Json(name = "Zone")
    var zone: Zone
)