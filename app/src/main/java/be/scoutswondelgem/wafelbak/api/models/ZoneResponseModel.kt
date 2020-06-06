package be.scoutswondelgem.wafelbak.api.models

import com.squareup.moshi.Json

data class ZoneResponseModel(
    @field:Json(name = "id")
    var zoneId: Int,
    @field:Json(name = "zoneName")
    var zoneName: String
)
