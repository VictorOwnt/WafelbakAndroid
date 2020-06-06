package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Zones")
data class ZoneDataModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var zoneId: Int,
    var zoneName: String
)