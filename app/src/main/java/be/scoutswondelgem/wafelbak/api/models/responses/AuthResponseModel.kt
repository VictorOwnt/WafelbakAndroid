package be.scoutswondelgem.wafelbak.api.models.responses

import be.scoutswondelgem.wafelbak.api.models.enums.UserRole
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class AuthResponseModel(
    @field:Json(name = "id")
    var userId: Int,
    @field:Json(name = "firstName")
    var firstName: String?,
    @field:Json(name = "lastName")
    var lastName: String?,
    @field:Json(name = "email")
    var email: String,
    @field:Json(name = "birthday")
    var birthday: Date?,
    @field:Json(name = "role")
    var role: UserRole,
    @field:Json(name = "hash")
    var hash: String,
    @field:Json(name = "salt")
    var salt: String,
    @field:Json(name = "token")
    var token: String
)