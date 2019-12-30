package be.scoutswondelgem.wafelbak.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.util.*

@Entity(tableName = "Users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    @field:Json(name="id")
    var userId: Int,
    @field:Json(name="firstName")
    var firstName: String,
    @field:Json(name="lastName")
    var lastName: String,
    @field:Json(name="email")
    var email: String,
    @field:Json(name="birthday")
    var birthday: Date,
    @ColumnInfo(name="admin")
    @field:Json(name="admin")
    var isAdmin: Boolean = false,
    @field:Json(name="street")
    var street: String,               //TODO embedded address?
    @field:Json(name="streetNumber")
    var streetNumber: Int,
    @field:Json(name="streetExtra")
    var streetExtra: String?,
    @field:Json(name="postalCode")
    var postalCode: Int,
    @field:Json(name="city")
    var city: String,
    @field:Json(name="token")
    var token: String?
    // val imgUrl: String? = null,
)