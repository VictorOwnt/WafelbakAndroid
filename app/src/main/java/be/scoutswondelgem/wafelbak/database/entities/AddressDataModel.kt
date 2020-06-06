package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey

@Entity(
    tableName = "Addresses", foreignKeys = [
        ForeignKey(
            entity = StreetDataModel::class,
            parentColumns = ["id"], // Verwijst naar attribuut van parent
            childColumns = ["StreetId"],
            onDelete = CASCADE
        ), // Als de straat verwijderd is, mag het adres niet meer bestaan
        ForeignKey(
            entity = UserDataModel::class,
            parentColumns = ["id"], // Verwijst naar attribuut van parent
            childColumns = ["UserId"],
            onDelete = SET_NULL // Als de user verwijderd is , mag het adres blijven bestaan
        )]
)
data class AddressDataModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var addressId: Int,
    var streetNumber: Int,
    var streetExtra: String?,
    @ColumnInfo(name = "StreetId")
    var streetId: Int,
    @ColumnInfo(name = "UserId")
    var userId: Int?
)