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

    fun register(firstName: String, lastName: String, email: String, password: String, birthday: String, street: String,
                 streetNumber: Int, streetExtra: String?, postalCode: Int, city: String):Single<User> {
        return wafelbakApi.register(firstName, lastName, email, password, birthday, street, streetNumber, streetExtra, postalCode, city)
    }

    fun isValidEmail(email: String, oldEmail: String?): Single<Boolean> {
        return wafelbakApi.isValidEmail(email, oldEmail)
    }

    fun editProfile(authToken:String, id: Int, firstName: String, lastName: String, email: String, password: String, birthday: String, street: String,
                    streetNumber: Int, streetExtra: String?, postalCode: Int, city: String):Single<User> {
        return wafelbakApi.editProfile(authToken, id, firstName, lastName, email, password, birthday, street, streetNumber, streetExtra, postalCode, city)
    }

    fun getUserByEmail(authToken: String, email: String): Single<User> {
        return wafelbakApi.getUserByEmail(authToken, email)
    }
}