package be.scoutswondelgem.wafelbak.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import be.scoutswondelgem.wafelbak.models.Order

@Dao
interface OrderDao {
    @Insert
    fun insert(order: Order)

    @Update
    fun update(order: Order)

    @Query("SELECT * from Orders")
    fun getAllOrders(): LiveData<List<Order>>

    @Query("SELECT * FROM Orders WHERE desiredDeliveryTime =:desiredDeliveryTime")
    fun getOrdersByDesiredDeliveryTime(desiredDeliveryTime: String): LiveData<List<Order>>?

    @Query("SELECT * FROM Orders WHERE amountOfWaffles =:amountOfWaffles")
    fun getOrdersByAmountOfWaffles(amountOfWaffles: Int): LiveData<List<Order>>?

    @Query("SELECT COUNT(*) FROM Orders")
    fun getRowCount(): Int

    @Query("DELETE FROM Orders")
    fun clearTable()

    @Delete
    fun deleteOrder(order: Order)

}