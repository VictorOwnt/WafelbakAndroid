package be.scoutswondelgem.wafelbak.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    @field:Json(name = "id")
    var cityId: Int,
    @field:Json(name = "cityName")
    var cityName: String,
    @field:Json(name = "postalCode")
    var postalCode: Int
)