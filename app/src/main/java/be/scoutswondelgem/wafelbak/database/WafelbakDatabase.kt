package be.scoutswondelgem.wafelbak.database

import android.content.Context
import androidx.room.*
import be.scoutswondelgem.wafelbak.database.daos.OrderDao
import be.scoutswondelgem.wafelbak.database.daos.UserDao
import be.scoutswondelgem.wafelbak.database.daos.UserWithOrdersDao
import be.scoutswondelgem.wafelbak.database.entities.DbOrder
import be.scoutswondelgem.wafelbak.database.entities.DbUser
import be.scoutswondelgem.wafelbak.util.DateConverter

@Database(entities = [DbUser::class, DbOrder::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class WafelbakDatabase : RoomDatabase() {

    abstract val orderDao: OrderDao // TODO geen functies?
    abstract val userDao: UserDao
    abstract val userWithOrdersDao: UserWithOrdersDao

    companion object {
        @Volatile
        private var INSTANCE: WafelbakDatabase? = null

        fun getInstance(context: Context): WafelbakDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WafelbakDatabase::class.java,
                        "WafelbakDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}