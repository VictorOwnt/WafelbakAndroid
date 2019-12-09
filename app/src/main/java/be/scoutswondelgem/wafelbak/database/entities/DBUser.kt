package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.*
import be.scoutswondelgem.wafelbak.models.User
import java.util.*

@Entity(tableName = "Users")
data class DBUser (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "UserId")
    var id: Int,

    @ColumnInfo(name = "FirstName")
    var firstName: String,

    @ColumnInfo(name = "LastName")
    var lastName: String,

    @ColumnInfo(name = "Email")
    var email: String,

    @ColumnInfo(name = "Admin")
    var isAdmin: Boolean = false,

    @ColumnInfo(name = "Birthday")
    var birthday: Date,

    @Embedded
    var dbAddress: DBAddress,

    @ColumnInfo(name = "Image")
    var imgUrl: String?,

    @ColumnInfo(name = "Orders")
    var dbOrders: MutableList<DBOrder> = mutableListOf(),

    @ColumnInfo(name = "Token")
    var token: String?
)

fun List<DBUser>.asDomainModel(): List<User> {
    return map{
        User(
            id = it.id,
            firstName = it.firstName,
            lastName = it.lastName,
            email = it.email,
            isAdmin = it.isAdmin,
            birthday = it.birthday,
            address = it.dbAddress.asDomainModel(),
            imgUrl = it.imgUrl,
            orders = it.dbOrders.asDomainModel().toMutableList(),
            token = it.token
        )
    }
}