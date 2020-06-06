package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Cities"
)
data class CityDataModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var cityId: Int,
    var cityName: String,
    var postalCode: Int
)