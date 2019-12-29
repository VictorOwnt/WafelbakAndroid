package be.scoutswondelgem.wafelbak.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import be.scoutswondelgem.wafelbak.models.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun autoUpdate(users: List<User>)

    @Update
    fun update(user: User)

    @Query("SELECT * from Users")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM Users WHERE email =:mail")
    fun getUserByEmail(mail: String): User?

    @Query("SELECT COUNT(*) FROM Users")
    fun getRowCount(): Int

    @Query("DELETE FROM Users")
    fun clearTable()

    @Delete
    fun deleteUser(user: User)

}