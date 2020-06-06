package be.scoutswondelgem.wafelbak.database.daos

import androidx.room.*
import be.scoutswondelgem.wafelbak.database.entities.UserDataModel
import io.reactivex.Flowable

@Dao
interface UserDao {
    @Query("SELECT * from Users")
    fun getAllUsers(): Flowable<List<UserDataModel>>

    @Query("SELECT * FROM Users WHERE id =:id")
    fun getUserById(id: Int): Flowable<UserDataModel?>

    @Query("SELECT * FROM Users WHERE email =:mail")
    fun getUserByEmail(mail: String): Flowable<UserDataModel?>

    /* TODO welke functies willen we hieronder ook offline kunnen?
    @Query("SELECT COUNT(*) FROM Users")
    fun getRowCount(): Int

    @Query("DELETE FROM Users")
    fun clearTable()

    @Delete
    fun deleteUser(user: UserDataModel)
    */
}