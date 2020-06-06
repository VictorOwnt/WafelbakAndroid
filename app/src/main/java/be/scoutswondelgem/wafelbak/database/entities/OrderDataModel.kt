package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import be.scoutswondelgem.wafelbak.models.enums.AmountOfWaffles
import be.scoutswondelgem.wafelbak.models.enums.DeliveryStatus
import be.scoutswondelgem.wafelbak.models.enums.DeliveryTime

@Entity(
    tableName = "Orders", foreignKeys = [
        ForeignKey(
            entity = AddressDataModel::class,
            parentColumns = ["id"], // Verwijst naar attribuut van parent
            childColumns = ["AddressId"],
            onDelete = CASCADE // Als adres verwijderd is, willen we geen bestellingen naar nergens
        )]
)
data class OrderDataModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var orderId: Int,
    @ColumnInfo(name = "name")
    var userName: String, // name on which the order is made
    var amountOfWaffles: AmountOfWaffles = AmountOfWaffles.FOUR,
    var desiredOrderTime: DeliveryTime = DeliveryTime.DOESNTMATTER,
    var status: DeliveryStatus = DeliveryStatus.NOTDELIVERED,
    var comment: String,
    @ColumnInfo(name = "AddressId")
    var addressId: Int
)