package be.scoutswondelgem.wafelbak.database.dataAccessObjects

import androidx.lifecycle.LiveData
import androidx.room.*
import be.scoutswondelgem.wafelbak.database.entities.Order

@Dao
interface OrderDao {

    @Insert
    fun insert(order: Order)

    @Update
    fun update(order: Order)

    @Query("SELECT * from Orders WHERE Id = :key")
    fun getById(key: String): Order?

    @Query("SELECT * from Orders WHERE User = :key")
    fun getByUser(key: String): LiveData<List<Order>>?

    @Query("DELETE FROM Orders")
    fun clear()

    @Query("SELECT * FROM Orders ORDER BY Id DESC")
    fun getAllOrders(): LiveData<List<Order>>
}