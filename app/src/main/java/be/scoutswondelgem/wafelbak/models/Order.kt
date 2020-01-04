package be.scoutswondelgem.wafelbak.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "Orders", foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["id"],
            childColumns = ["UserId"],
            onDelete = CASCADE)])
data class Order (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    @field:Json(name="id")
    var orderId: Int,
    @field:Json(name="amountOfWaffles")
    var amountOfWaffles: Int,
    @field:Json(name="desiredDeliveryTime")
    var desiredDeliveryTime: DeliveryDate,
    @field:Json(name="comment")
    var comment: String?,
    @ColumnInfo(name="UserId")
    @field:Json(name="UserId")
    var userId: Int?
)