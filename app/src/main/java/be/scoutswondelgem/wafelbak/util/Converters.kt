package be.scoutswondelgem.wafelbak.util

import androidx.room.*
import be.scoutswondelgem.wafelbak.database.dataAccessObjects.OrderDao
import be.scoutswondelgem.wafelbak.database.entities.DBOrder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class Converters {
    @Inject
    internal lateinit var orderDao: OrderDao
    private var format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.getDefault())

    @TypeConverter
    fun stringtoDate(stringDate: String): Date? {
        return try {
            format.parse(stringDate)
        }
        catch (e: ParseException) {
            null
        }
    }

    @TypeConverter
    fun dateToString(date: Date):String{
        return format.format(date)
    }

    @TypeConverter
    fun ordersToString(orders: List<DBOrder>): String {
        var output = ""
        orders.forEach{ order ->
            output.plus(order.id.toString() + ",")
        }
        return output
    }

    @TypeConverter
    fun stringToOrders(orders: String): List<DBOrder> {
        var orderList: MutableList<DBOrder> = mutableListOf()
        var orderIds = orders.split(',')
        orderIds.forEach{ orderId ->
            var order = orderDao.getById(orderId.toInt())
            if (order !== null) {
                orderList.add(order)
            }
        }
        return orderList
    }
}