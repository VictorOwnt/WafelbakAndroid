package be.scoutswondelgem.wafelbak.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import be.scoutswondelgem.wafelbak.database.entities.DbOrder

@Dao
interface OrderDao {
    @Insert
    fun insert(order: DbOrder)

    @Update
    fun update(order: DbOrder)

    @Query("SELECT * from Bestellingen")
    fun getAllOrders(): LiveData<List<DbOrder>>

    @Query("SELECT * FROM Bestellingen WHERE Liefst_Leveren_Tegen =:desiredDeliveryTime")
    fun getOrdersByDesiredDeliveryTime(desiredDeliveryTime: String): LiveData<List<DbOrder>>?

    @Query("SELECT * FROM Bestellingen WHERE Aantal_Wafels =:amountOfWaffles")
    fun getOrdersByAmountOfWaffles(amountOfWaffles: Int): LiveData<List<DbOrder>>?

    @Query("SELECT COUNT(*) FROM Bestellingen")
    fun getRowCount(): Int

    @Query("DELETE FROM Bestellingen")
    fun clearTable()

    @Delete
    fun deleteUser(order: DbOrder)

}