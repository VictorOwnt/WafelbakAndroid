package be.scoutswondelgem.wafelbak.repository

import androidx.lifecycle.LiveData
import be.scoutswondelgem.wafelbak.api.WafelbakApi
import be.scoutswondelgem.wafelbak.database.daos.OrderDao
import be.scoutswondelgem.wafelbak.models.Order
import io.reactivex.Observable
import io.reactivex.Single

class OrderRepository(private val wafelbakApi: WafelbakApi, private val orderDao: OrderDao) {
    //Data:
    val data: LiveData<List<Order>> = orderDao.getAllOrders()

    fun getOrdersForUser(authToken: String, id: Int): Observable<List<Order>> {
        return wafelbakApi.getOrdersByUserId(authToken, id)
    }

    fun getOrderById(authToken: String, id:Int): Single<Order> {
        return wafelbakApi.getOrderById(authToken, id)
    }

    fun updateOrder(authToken: String, order : Order ): Single<Order> {
        return wafelbakApi.updateOrder(authToken, order)
    }
}