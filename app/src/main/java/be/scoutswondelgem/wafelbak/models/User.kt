package be.scoutswondelgem.wafelbak.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "Users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var userId: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var birthday: Date,
    @ColumnInfo(name="admin")
    var isAdmin: Boolean = false,
    var street: String,               //TODO embedded address?
    var streetNumber: Int,
    var streetExtra: String?,
    var postalCode: Int,
    var city: String,
    var token: String?
    // val imgUrl: String? = null,
)