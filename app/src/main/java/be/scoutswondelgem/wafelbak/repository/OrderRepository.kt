package be.scoutswondelgem.wafelbak.repository

import androidx.lifecycle.LiveData
import be.scoutswondelgem.wafelbak.api.WafelbakApi
import be.scoutswondelgem.wafelbak.database.daos.OrderDao
import be.scoutswondelgem.wafelbak.models.DeliveryDate
import be.scoutswondelgem.wafelbak.models.Order
import be.scoutswondelgem.wafelbak.models.OrderAndUser
import io.reactivex.Observable
import io.reactivex.Single

class OrderRepository(private val wafelbakApi: WafelbakApi, private val orderDao: OrderDao) {
    //Data:
    val data: LiveData<List<Order>> = orderDao.getAllOrders()

    fun getOrdersJoined(authToken: String):Observable<List<OrderAndUser>> {
        return wafelbakApi.getOrdersJoined(authToken)
    }

    fun getOrdersForUser(authToken: String, id: Int): Observable<List<Order>> {
        return wafelbakApi.getOrdersByUserId(authToken, id)
    }

    fun getOrderById(authToken: String, id:Int): Single<Order> {
        return wafelbakApi.getOrderById(authToken, id)
    }

    fun updateOrder(authToken: String, order : Order ): Single<Order> {
        return wafelbakApi.updateOrder(authToken, order)
    }

    fun completeOrder(authToken: String, order : Order ): Single<Order> {
        return wafelbakApi.completeOrder(authToken, order)
    }

    fun deleteOrder(authToken: String, order: Order): Single<Order> {
        return wafelbakApi.deleteOrder(authToken, order)
    }

    fun createOrder(authToken: String, amountOfWaffles: Int, desiredDeliveryTime: DeliveryDate, comment: String, userId: Int): Single<Order> {
        return wafelbakApi.createOrder(authToken, amountOfWaffles, desiredDeliveryTime.levertijd, comment, userId)
    }
}