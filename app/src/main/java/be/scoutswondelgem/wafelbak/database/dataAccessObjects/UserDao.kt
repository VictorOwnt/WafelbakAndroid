package be.scoutswondelgem.wafelbak.database.dataAccessObjects

import androidx.lifecycle.LiveData
import androidx.room.*
import be.scoutswondelgem.wafelbak.database.entities.User

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * from Users WHERE Id = :key")
    fun getById(key: Int): User?

    @Query("SELECT * from Users WHERE Email = :key")
    fun getByEmail(key: String): User?

    @Query("DELETE FROM Users")
    fun clear()

    @Query("SELECT * FROM Users ORDER BY Id DESC")
    fun getAllUsers(): LiveData<List<User>>
}