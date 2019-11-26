package be.scoutswondelgem.wafelbak.repositories

import androidx.lifecycle.LiveData
import be.scoutswondelgem.wafelbak.database.dataAccessObjects.UserDao
import be.scoutswondelgem.wafelbak.database.entities.User
import javax.inject.Inject


class UserRepository {
    @Inject
    internal lateinit var userDao: UserDao

    fun getUserData(): LiveData<List<User>>? {
        return userDao.getAllUsers()
    }

    fun deleteAllUsers() {
        userDao.clear()
    }

    fun getUserByEmail(email: String): User?{
        return userDao.getByEmail(email)
    }

    fun getUserById(id: Int) : User? {
        return userDao.getById(id)
    }

    fun updateUser(user: User) {
        userDao.update(user)
    }

    fun insertData(user: User) {
        userDao.insert(user)
    }
}