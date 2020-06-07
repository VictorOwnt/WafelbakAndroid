package be.scoutswondelgem.wafelbak.api.models.responses

import be.scoutswondelgem.wafelbak.api.models.City
import be.scoutswondelgem.wafelbak.api.models.Zone
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
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