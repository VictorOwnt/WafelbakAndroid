package be.scoutswondelgem.wafelbak.repository

import androidx.lifecycle.LiveData
import be.scoutswondelgem.wafelbak.api.WafelbakApi
import be.scoutswondelgem.wafelbak.database.daos.UserDao
import be.scoutswondelgem.wafelbak.models.User
import io.reactivex.Single

class UserRepository(private val wafelbakApi: WafelbakApi, private val userDao: UserDao) {
    //Data:
    val data: LiveData<List<User>> = userDao.getAllUsers()

    fun login(email: String, password: String): Single<User> {
        return wafelbakApi.login(email, password)
    }

    // fun isValidEmail(email: String) = wafelbakApi.isValidEmail(email) // volgens mij kan dit zelf compleet uit de applicatie
    // suspend fun getUsers() = wafelbakApi.getUsers()
    // suspend fun getUserByEmail(email: String) = wafelbakApi.getUserByEmail(email)
}