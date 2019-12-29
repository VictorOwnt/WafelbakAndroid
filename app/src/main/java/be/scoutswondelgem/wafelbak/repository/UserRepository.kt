package be.scoutswondelgem.wafelbak.repository

import androidx.lifecycle.LiveData
import be.scoutswondelgem.wafelbak.api.WafelbakApi
import be.scoutswondelgem.wafelbak.database.daos.UserDao
import be.scoutswondelgem.wafelbak.models.User

class UserRepository(private val wafelbakApi: WafelbakApi, private val userDao: UserDao) {
    //Data:
    val data: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun login(email: String, password: String): User {
        return wafelbakApi.login(email, password)
    }

    // fun isValidEmail(email: String) = wafelbakApi.isValidEmail(email)
    // suspend fun getUsers() = wafelbakApi.getUsers()
    // suspend fun getUserByEmail(email: String) = wafelbakApi.getUserByEmail(email)
}