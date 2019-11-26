package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.*
import java.util.*

@Entity(tableName = "Users")
data class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
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
    var birthday: Date?,

    @Embedded
    var address: Address,

    @ColumnInfo(name = "Image")
    var imgUrl: String? = null,

    @Ignore
    var orders: MutableList<Order>? = mutableListOf(),

    @ColumnInfo(name = "Token")
    var token: String? = null
)
{
    constructor() : this(0, "", "", "", false, null, Address("",0,""), "", null, "")
}