package be.scoutswondelgem.wafelbak.api.models.responses

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateStreetResponseModel(
    @field:Json(name = "id")
    var streetId: Int,
    @field:Json(name = "streetName")
    var streetName: String,
    @field:Json(name = "CityId")
    var cityId: Int
)