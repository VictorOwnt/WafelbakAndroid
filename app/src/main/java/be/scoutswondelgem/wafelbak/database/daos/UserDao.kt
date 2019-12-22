package be.scoutswondelgem.wafelbak.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import be.scoutswondelgem.wafelbak.database.entities.DbUser

@Dao
interface UserDao {
    @Insert
    fun insert(user: DbUser)

    @Update
    fun update(user: DbUser)

    @Query("SELECT * from Users")
    fun getAllUsers(): LiveData<List<DbUser>>

    @Query("SELECT * FROM Users WHERE Email =:mail")
    fun getUserByEmail(mail: String): DbUser?

    @Query("SELECT COUNT(*) FROM Users")
    fun getRowCount(): Int

    @Query("DELETE FROM Users")
    fun clearTable()

    @Delete
    fun deleteUser(user: DbUser)

}