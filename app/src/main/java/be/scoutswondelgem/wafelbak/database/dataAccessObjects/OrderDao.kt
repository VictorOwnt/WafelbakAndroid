package be.scoutswondelgem.wafelbak.database.dataAccessObjects

import androidx.lifecycle.LiveData
import androidx.room.*
import be.scoutswondelgem.wafelbak.database.entities.DBOrder

@Dao
interface OrderDao {

    @Insert
    fun insert(dbOrder: DBOrder)

    @Update
    fun update(dbOrder: DBOrder)

    @Query("SELECT * from Orders WHERE OrderId = :key")
    fun getById(key: Int): DBOrder?

    /*
    @Query("SELECT * from Orders WHERE DBUser = :key")
    fun getByUser(key: String): LiveData<List<DBOrder>>?*/

    @Query("DELETE FROM Orders")
    fun clear()

    @Query("SELECT * FROM Orders ORDER BY OrderId DESC")
    fun getAllOrders(): LiveData<List<DBOrder>>
}