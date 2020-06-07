package be.scoutswondelgem.wafelbak.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Zone(
    @field:Json(name = "id")
    var zoneId: Int,
    @field:Json(name = "zoneName")
    var zoneName: String
)