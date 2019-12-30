package be.scoutswondelgem.wafelbak.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.scoutswondelgem.wafelbak.models.User
import be.scoutswondelgem.wafelbak.repository.UserRepository
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.security.auth.login.LoginException


class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    //LoadingVisibility:
    val loadingVisibility = MutableLiveData<Int>()
    val contentEnabled = MutableLiveData<Boolean>()

    //RxJava
    private var disposables = CompositeDisposable()

    init {
        loadingVisibility.value = View.GONE
        contentEnabled.value = true
    }

    fun login(email: String, password: String): User {
        try{
            onRetrieveStart()
            return userRepository.login(email, password)
                .doOnError { error -> onRetrieveError(error) }
                .blockingGet()
        } catch (e: Exception) {
            throw LoginException((e as HttpException).response()!!.errorBody()!!.string())
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