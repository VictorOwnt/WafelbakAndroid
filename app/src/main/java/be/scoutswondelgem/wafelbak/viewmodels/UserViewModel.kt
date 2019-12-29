package be.scoutswondelgem.wafelbak.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.scoutswondelgem.wafelbak.models.User
import be.scoutswondelgem.wafelbak.repository.UserRepository
import kotlinx.coroutines.*
import retrofit2.HttpException


class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    //LoadingVisibility:
    val loadingVisibility = MutableLiveData<Int>()
    val contentEnabled = MutableLiveData<Boolean>()

    //CoroutineContext: MOMENTEEL ONGEBRUIKT
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    init {
        loadingVisibility.value = View.GONE
        contentEnabled.value = true
    }

    suspend fun login(email: String, password: String): User {
        try{
            onRetrieveStart()
            return withContext(Dispatchers.IO) {
                return@withContext userRepository.login(email, password)
            }
        } catch (e: Exception) {
            throw java.lang.Exception((e as HttpException).response()!!.errorBody()!!.string())
        } finally {
            onRetrieveFinish()
        }
    }

    private fun onRetrieveStart() {
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveFinish() {
        loadingVisibility.value = View.GONE
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}