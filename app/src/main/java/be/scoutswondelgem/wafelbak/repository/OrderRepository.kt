package be.scoutswondelgem.wafelbak.repository

import be.scoutswondelgem.wafelbak.api.WafelbakApiClient
import be.scoutswondelgem.wafelbak.api.models.responses.OrderResponseModel
import io.reactivex.Flowable
import io.reactivex.Single

class OrderRepository(private val wafelbakApiClient: WafelbakApiClient) {
    /*//Data:
    val data: LiveData<List<Order>> = orderDao.getAllOrders()

    fun getOrders(authToken: String):Observable<List<Order>> {
        return wafelbakApiClient.getOrders(authToken)
    }

    fun getOrdersJoined(authToken: String):Observable<List<OrderAndUser>> {
        return wafelbakApiClient.getOrdersJoined(authToken)
    }*/

    //TODO aanpassen met juiste call
    fun getOrdersForUser(authToken: String/*, id: Int*/): Single<List<OrderResponseModel>> {
        return wafelbakApiClient.getOrders(authToken)
    }

    /*
    fun getOrderById(authToken: String, id:Int): Single<Order> {
        return wafelbakApiClient.getOrderById(authToken, id)
    }

    fun updateOrder(authToken: String, order : Order ): Single<Order> {
        return wafelbakApiClient.updateOrder(authToken, order)
    }

    fun completeOrder(authToken: String, order : Order ): Single<Order> {
        return wafelbakApiClient.completeOrder(authToken, order)
    }

    fun deleteOrder(authToken: String, order: Order): Single<Order> {
        return wafelbakApiClient.deleteOrder(authToken, order)
    }

    fun createOrder(authToken: String, amountOfWaffles: Int, desiredDeliveryTime: DeliveryTime, comment: String, userId: Int): Single<Order> {
        return wafelbakApiClient.createOrder(authToken, amountOfWaffles, desiredDeliveryTime.levertijd, comment, userId)
    }*/
}