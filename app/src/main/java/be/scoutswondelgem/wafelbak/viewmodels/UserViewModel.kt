package be.scoutswondelgem.wafelbak.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import be.scoutswondelgem.wafelbak.api.WafelbakApi
import be.scoutswondelgem.wafelbak.base.BaseViewModel
import be.scoutswondelgem.wafelbak.database.entities.User
import com.orhanobut.logger.Logger
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject
import javax.security.auth.login.LoginException

class UserViewModel : BaseViewModel() {

    val user = MutableLiveData<User>()
    val loadingVisibility = MutableLiveData<Int>()
    val contentEnabled = MutableLiveData<Boolean>()

    @Inject
    lateinit var wafelbakApi: WafelbakApi
    private var disposables = CompositeDisposable()

    init {
        loadingVisibility.value = View.GONE
        contentEnabled.value = true
    }

    /**
     * Signs in existing user
     *
     * @param email
     * @param password
     * @return user with token
     */
    fun login(email: String, password: String): User {
        try {
            onRetrieveStart()
            return wafelbakApi.login(email, password)
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

    /**
     * Disposes the subscription when the [BaseViewModel] is no longer used.
     */
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}