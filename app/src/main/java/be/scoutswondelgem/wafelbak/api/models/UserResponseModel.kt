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

    //TODO DIT IS ALLEMAAL VAN ADDRESS ==> Embedded address maken of gewoon Address intypen en adapter toeveogen voor address, en address toevoegen in gewone models
    @field:Json(name = "street")
    var street: String,
    @field:Json(name = "streetNumber")
    var streetNumber: Int,
    @field:Json(name = "streetExtra")
    var streetExtra: String?,
    @field:Json(name = "postalCode")
    var postalCode: Int,
    @field:Json(name = "city")
    var city: String
)