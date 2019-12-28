package be.scoutswondelgem.wafelbak.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.scoutswondelgem.wafelbak.models.User
import be.scoutswondelgem.wafelbak.repository.UserRepository
import kotlinx.coroutines.*
import org.koin.dsl.module.module
import retrofit2.HttpException
import retrofit2.await
import javax.security.auth.login.LoginException

val viewModelModule = module {
    factory { UserViewModel(get()) }
}

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //val user = MutableLiveData<User>()
    val loadingVisibility = MutableLiveData<Int>()
    val contentEnabled = MutableLiveData<Boolean>()

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
    suspend fun login(email: String, password: String): User {
        try {
            onRetrieveStart()
            return withContext(Dispatchers.IO) {
                userRepository.login(email, password).await()
            }

        } catch (e: Exception) {
            throw LoginException((e as HttpException).response()!!.errorBody()!!.string())
        } finally {
            onRetrieveStop()
        }
    }

    /*
    fun getUsers(): Observable<List<User>> {
        try {
            onRetrieveStart()
            return kolvApi.getUsers()

        } catch (e: Exception) {
            throw java.lang.Exception((e as HttpException).response()!!.errorBody()!!.string())
        } finally {
            onRetrieveFinish()
        }

    }

    fun getClients(): Observable<List<User>> {
        try {
            onRetrieveStart()
            return kolvApi.getClients()

        } catch (e: Exception) {
            throw java.lang.Exception((e as HttpException).response()!!.errorBody()!!.string())
        } finally {
            onRetrieveFinish()
        }

    }
     */

    private fun onRetrieveStart() {
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveStop() {
        loadingVisibility.value = View.GONE
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}