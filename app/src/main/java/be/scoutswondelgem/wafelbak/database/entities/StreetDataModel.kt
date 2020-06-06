package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey

@Entity(
    tableName = "Streets", foreignKeys = [
        ForeignKey(
            entity = CityDataModel::class,
            parentColumns = ["id"], // Verwijst naar attribuut van parent
            childColumns = ["CityId"],
            onDelete = CASCADE // Als de stad verwijderd wordt, bestaan de straten niet meer
        ),
        ForeignKey(
            entity = ZoneDataModel::class,
            parentColumns = ["id"], // Verwijst naar attribuut van parent
            childColumns = ["ZoneId"],
            onDelete = SET_NULL // Als de bakpost verwijderd is, mag de straat blijven bestaan
        )]
)
data class StreetDataModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var streetId: Int,
    var streetName: String,
    @ColumnInfo(name = "CityId")
    var cityId: Int,
    @ColumnInfo(name = "ZoneId")
    var zoneId: Int
)