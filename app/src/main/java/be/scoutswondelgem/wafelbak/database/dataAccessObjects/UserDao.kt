package be.scoutswondelgem.wafelbak.database.dataAccessObjects

import androidx.lifecycle.LiveData
import androidx.room.*
import be.scoutswondelgem.wafelbak.database.entities.DBUser

@Dao
interface UserDao {

    @Insert
    fun insert(dbUser: DBUser)

    @Update
    fun update(dbUser: DBUser)

    @Query("SELECT * from Users WHERE UserId = :key")
    fun getById(key: Int): DBUser?

    @Query("SELECT * from Users WHERE Email = :key")
    fun getByEmail(key: String): DBUser?

    @Query("DELETE FROM Users")
    fun clear()

    @Query("SELECT * FROM Users ORDER BY UserId DESC")
    fun getAllUsers(): LiveData<List<DBUser>>
}