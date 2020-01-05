package be.scoutswondelgem.wafelbak.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.scoutswondelgem.wafelbak.models.DeliveryDate
import be.scoutswondelgem.wafelbak.models.Order
import be.scoutswondelgem.wafelbak.models.OrderAndUser
import be.scoutswondelgem.wafelbak.repository.OrderRepository
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import java.lang.Exception

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {
    //LoadingVisibility:
    val loadingVisibility = MutableLiveData<Int>()
    val contentEnabled = MutableLiveData<Boolean>()

    //RxJava
    private var disposables = CompositeDisposable()

    init {
        loadingVisibility.value = View.GONE
        contentEnabled.value = true
    }

    fun getOrders(authToken: String): List<Order> {
        try{
            onRetrieveStart()
            return orderRepository.getOrders(authToken)
                .doOnError {error -> onRetrieveError(error)}
                .blockingFirst()
        } catch (e : Exception) {
            Logger.e((e as HttpException).response()!!.errorBody()!!.string())
            return emptyList()
        } finally {
            onRetrieveFinish()
        }
    }

    fun getOrdersJoined(authToken: String): List<OrderAndUser> {
        try{
            onRetrieveStart()
            return orderRepository.getOrdersJoined(authToken)
                .doOnError {error -> onRetrieveError(error)}
                .blockingFirst()
        } catch (e : Exception) {
            Logger.e((e as HttpException).response()!!.errorBody()!!.string())
            return emptyList()
        } finally {
            onRetrieveFinish()
        }
    }

    fun getOrdersForCurrentUser(authToken: String, id: Int): List<Order> {
        try{
            onRetrieveStart()
            return orderRepository.getOrdersForUser(authToken, id)
                .doOnError {error -> onRetrieveError(error)}
                .blockingFirst()
        } catch (e : Exception) {
            Logger.e((e as HttpException).response()!!.errorBody()!!.string())
            return emptyList()
        } finally {
            onRetrieveFinish()
        }
    }

    fun getOrderById(authToken : String, id:Int): Order {
        try{
            onRetrieveStart()
            return orderRepository.getOrderById(authToken, id)
                .doOnError {error -> onRetrieveError(error)}
                .blockingGet()
        } catch (e : Exception) {
            throw Exception((e as HttpException).response()!!.errorBody()!!.string())
        } finally {
            onRetrieveFinish()
        }
    }

    fun updateOrder(authToken: String, order: Order): Order {
        try {
            onRetrieveStart()
            return orderRepository.updateOrder(authToken, order)
                .doOnError {error -> onRetrieveError(error)}
                .blockingGet()
        } catch (e : Exception) {
            throw Exception((e as HttpException).response()!!.errorBody()!!.string())
        } finally {
            onRetrieveFinish()
        }
    }

    fun completeOrder(authToken: String, order: Order): Order {
        try {
            onRetrieveStart()
            return orderRepository.completeOrder(authToken, order)
                .doOnError {error -> onRetrieveError(error)}
                .blockingGet()
        } catch (e : Exception) {
            throw Exception((e as HttpException).response()!!.errorBody()!!.string())
        } finally {
            onRetrieveFinish()
        }
    }

    fun deleteOrder(authToken: String, order:Order): Order {
        try {
            onRetrieveStart()
            return orderRepository.deleteOrder(authToken, order)
                .doOnError {error -> onRetrieveError(error)}
                .blockingGet()
        } catch(e: Exception) {
            throw Exception((e as HttpException).response()!!.errorBody()!!.string())
        } finally {
            onRetrieveFinish()
        }
    }

    fun createOrder(authToken: String, amountOfWaffles: Int, desiredDeliveryTime: DeliveryDate, comment: String, userId: Int): Order {
        try {
            onRetrieveStart()
            return orderRepository.createOrder(authToken, amountOfWaffles, desiredDeliveryTime, comment, userId)
                .doOnError {error -> onRetrieveError(error)}
                .blockingGet()
        } catch(e: Exception) {
            throw Exception((e as HttpException).response()!!.errorBody()!!.string())
        } finally {
            onRetrieveFinish()
        }
    }

    private fun onRetrieveError(error: Throwable) {
        Logger.e(error.message!!)
    }


    private fun onRetrieveStart() {
        loadingVisibility.value = View.VISIBLE
        contentEnabled.value = false
    }

    private fun onRetrieveFinish() {
        loadingVisibility.value = View.GONE
        contentEnabled.value = true
    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}