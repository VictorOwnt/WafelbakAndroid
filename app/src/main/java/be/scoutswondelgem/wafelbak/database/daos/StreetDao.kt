package be.scoutswondelgem.wafelbak.database.daos

import androidx.room.*
import be.scoutswondelgem.wafelbak.database.entities.StreetDataModel
import io.reactivex.Flowable

@Dao
interface StreetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //this is the correct usage of the insert, this is because of the generated key
    fun insert(street: StreetDataModel)

    @Update
    fun update(street: StreetDataModel)

    @Query("SELECT * from Streets")
    fun getAllStreets(): Flowable<List<StreetDataModel>>

    @Query("SELECT * from Streets WHERE id =:id")
    fun getStreetById(id: Int): Flowable<StreetDataModel?>

    @Query("SELECT * from Streets WHERE id =:name")
    fun getStreetsByName(name: String): Flowable<List<StreetDataModel?>> //list because two different cities can have same street, otherwise streetname is unique

    //TODO get streets by cityname, by zonename

    @Delete
    fun deleteStreet(street: StreetDataModel)

}