package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import be.scoutswondelgem.wafelbak.models.Order
import java.util.*

@Entity(tableName = "Orders",
    foreignKeys = [
        ForeignKey(entity = DBUser::class,
            parentColumns = ["UserId"],
            childColumns = ["OrderId"],
            onDelete = CASCADE)])
data class DBOrder(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "OrderId")
    var id: Int,

    @ColumnInfo(name = "Waffles")
    var amountOfWaffles: Int,

    @ColumnInfo(name = "DateOrdered")
    var dateOrdered: Date,

    @ColumnInfo(name = "DeliveryTime")
    var desiredDeliveryTime: String
)

fun List<DBOrder>.asDomainModel(): List<Order> {
    return map{
        Order(
            id = it.id,
            amountOfWaffles = it.amountOfWaffles,
            dateOrdered = it.dateOrdered,
            desiredDeliveryTime = it.desiredDeliveryTime
        )
    }
}
