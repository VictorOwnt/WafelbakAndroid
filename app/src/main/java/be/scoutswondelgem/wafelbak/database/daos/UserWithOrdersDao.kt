package be.scoutswondelgem.wafelbak.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import be.scoutswondelgem.wafelbak.database.entities.DbUserWithOrders

@Dao
interface UserWithOrdersDao {
    @Query("SELECT * from Users")
    fun getUsersWithOrders(): LiveData<List<DbUserWithOrders>>

    @Query("SELECT * FROM Users WHERE UserId =:userId")
    fun getUserWithOrders(userId: Long): DbUserWithOrders?

    //TODO moet nog uitgest worden, moeten we geen querys schrijven met INNER JOIN?

}