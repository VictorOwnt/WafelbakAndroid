package be.scoutswondelgem.wafelbak.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.scoutswondelgem.wafelbak.models.Order
import be.scoutswondelgem.wafelbak.repository.OrderRepository
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import java.lang.Exception

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {
    //LoadingVisibility:
    val loadingVisibility = MutableLiveData<Int>()
    //val contentEnabled = MutableLiveData<Boolean>()

    //RxJava
    private var disposables = CompositeDisposable()

    init {
        loadingVisibility.value = View.GONE
        //contentEnabled.value = true
    }

    fun getOrdersForCurrentUser(authToken: String, id: Int): List<Order> {
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
    }

    private fun onRetrieveError(error: Throwable) {
        Logger.e(error.message!!)
    }

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