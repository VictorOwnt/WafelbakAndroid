package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Bestellingen")
data class DbOrder(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="OrderId")
    val orderId: Long = 0L,
    @ColumnInfo(name="Aantal_Wafels")
    val amountOfWaffles: Int,
    @ColumnInfo(name="Besteld_Op")
    val dateOrdered: Date,
    @ColumnInfo(name="Liefst_Leveren_Tegen")
    val desiredDeliveryTime: String,
    @ColumnInfo(name="Opmerkingen")
    val comment: String? = null,
    @ColumnInfo(name= "FK_UserId")
    val userId: Long
)

