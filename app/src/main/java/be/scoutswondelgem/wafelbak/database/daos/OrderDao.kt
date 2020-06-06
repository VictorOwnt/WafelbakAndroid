package be.scoutswondelgem.wafelbak.database.daos

import androidx.room.*
import be.scoutswondelgem.wafelbak.database.entities.OrderDataModel
import io.reactivex.Flowable

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) //this is the correct usage of the insert, this is because of the generated key
    fun insert(order: OrderDataModel)

    @Update
    fun update(order: OrderDataModel)

    @Query("SELECT * from Orders")
    fun getAllOrders(): Flowable<List<OrderDataModel>>

    @Query("SELECT * FROM Orders WHERE id =:id")
    fun getOrderById(id: Int): Flowable<OrderDataModel?>

    @Query("SELECT * FROM Orders WHERE status =:status")
    fun getOrderByStatus(status: String): Flowable<List<OrderDataModel?>>


    /* TODO functionaliteit bestaat nog iet op api
    @Query("SELECT * FROM Orders WHERE desiredOrderTime =:desiredOrderTime")
    fun getOrdersByDesiredDeliveryTime(desiredOrderTime: String): Flowable<List<OrderDataModel>>?

    @Query("SELECT * FROM Orders WHERE amountOfWaffles =:amountOfWaffles")
    fun getOrdersByAmountOfWaffles(amountOfWaffles: Int): Flowable<List<OrderDataModel>>?

    @Query("SELECT COUNT(*) FROM Orders")
    fun getRowCount(): Int

    @Query("DELETE FROM Orders")s
    fun clearTable()
     */

    @Delete
    fun deleteOrder(order: OrderDataModel)

}