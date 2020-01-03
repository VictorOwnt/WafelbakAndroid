package be.scoutswondelgem.wafelbak.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.scoutswondelgem.wafelbak.models.Order
import be.scoutswondelgem.wafelbak.repository.OrderRepository
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.lang.Exception

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {
    //Order(s)
    //val orders = MutableLiveData<List<Order>>()
    //val order = MutableLiveData<Order>()

    //LoadingVisibility:
    val loadingVisibility = MutableLiveData<Int>()
    val contentEnabled = MutableLiveData<Boolean>()

    //RxJava
    private var disposables = CompositeDisposable()

    init {
        loadingVisibility.value = View.GONE
        //contentEnabled.value = true
    }

    fun getOrdersForCurrentUser(authToken: String, id: Int):List<Order> {
        try{
            onRetrieveStart()
            return orderRepository.getOrdersForUser(authToken, id)
                .doOnError {error -> onRetrieveError(error)}
                .blockingFirst()
        } catch (e : Exception) {
            throw Exception((e as HttpException).response()!!.errorBody()!!.string())
        } finally {
            onRetrieveFinish()
        }
        /* disposables.add(
            orderRepository.getOrdersForUser(authToken, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveStart() }
                .doOnTerminate { onRetrieveFinish() }
                .subscribe(
                    { result -> onRetrieveListSuccess(result)},
                    { error -> onRetrieveError(error)}
                )
        )*/
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

    fun updateOrder(authToken: String, order: Order): Array<Int> {
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

    private fun onRetrieveError(error: Throwable) {
        Logger.e(error.message!!)
    }

    /*
    private fun onRetrieveListSuccess(results: List<Order>) {
        orders.value = results
        Logger.i(results.toString())
    }*/

    private fun onRetrieveStart() {
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveFinish() {
        loadingVisibility.value = View.GONE
    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}