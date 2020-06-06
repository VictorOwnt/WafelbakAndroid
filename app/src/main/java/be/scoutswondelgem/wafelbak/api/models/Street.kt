package be.scoutswondelgem.wafelbak.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Street(
    @field:Json(name = "streetName")
    var streetName: String,
    @field:Json(name = "City")
    var city: City
)