package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import be.scoutswondelgem.wafelbak.models.enums.UserRole
import java.util.*

@Entity(tableName = "Users")
data class UserDataModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var userId: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var birthday: Date,
    var role: UserRole = UserRole.USER,
    var hash: String,
    var salt: String,
    var token: String?
)