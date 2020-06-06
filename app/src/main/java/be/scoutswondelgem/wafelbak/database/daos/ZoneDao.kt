package be.scoutswondelgem.wafelbak.database.daos

import androidx.room.*
import be.scoutswondelgem.wafelbak.database.entities.ZoneDataModel
import io.reactivex.Flowable

@Dao
interface ZoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //this is the correct usage of the insert, this is because of the generated key
    fun insert(zone: ZoneDataModel)

    @Update
    fun update(zone: ZoneDataModel)

    @Query("SELECT * from Zones")
    fun getAllZones(): Flowable<List<ZoneDataModel>?>

    @Query("SELECT * from Zones WHERE id =:id")
    fun getZoneById(id: Int): Flowable<ZoneDataModel?>

    @Delete
    fun deleteZone(zone: ZoneDataModel)
}