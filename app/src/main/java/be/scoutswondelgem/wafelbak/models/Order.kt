package be.scoutswondelgem.wafelbak.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "Orders", foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["id"],
            childColumns = ["UserId"],
            onDelete = CASCADE)])
data class Order (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var orderId: Int,
    var amountOfWaffles: Int,
    var desiredDeliveryTime: DeliveryDate,
    var comment: String?,
    @ColumnInfo(name="UserId")
    var userId: Int
)