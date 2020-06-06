package be.scoutswondelgem.wafelbak.api.models

import be.scoutswondelgem.wafelbak.models.enums.UserRole
import com.squareup.moshi.Json
import java.util.*

data class UserResponseModel(
    @field:Json(name = "id")
    var userId: Int,
    @field:Json(name = "firstName")
    var firstName: String,
    @field:Json(name = "lastName")
    var lastName: String,
    @field:Json(name = "email")
    var email: String,
    @field:Json(name = "birthday")
    var birthday: Date,
    @field:Json(name = "role")
    var role: UserRole,
    @field:Json(name = "Address")
    var address: Address
)