package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*

@Entity(tableName = "Orders",
    foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["Id"],
            childColumns = ["User"],
            onDelete = CASCADE)])
data class Order(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "Id")
    val id: Int,

    @ColumnInfo(name = "Waffles")
    val amountOfWaffles: Int,

    @ColumnInfo(name = "DateOrdered")
    val dateOrdered: Date,

    @ColumnInfo(name = "DeliveryTime")
    val desiredDeliveryTime: String,

    @ColumnInfo(name = "User")
    val userId: String
)
