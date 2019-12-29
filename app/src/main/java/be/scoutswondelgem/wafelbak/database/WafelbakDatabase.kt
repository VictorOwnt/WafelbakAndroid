package be.scoutswondelgem.wafelbak.database

import android.content.Context
import androidx.room.*
import be.scoutswondelgem.wafelbak.database.daos.OrderDao
import be.scoutswondelgem.wafelbak.database.daos.UserDao
import be.scoutswondelgem.wafelbak.models.Order
import be.scoutswondelgem.wafelbak.models.User
import be.scoutswondelgem.wafelbak.util.Converters

@Database(entities = [User::class, Order::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WafelbakDatabase : RoomDatabase() {

    abstract val orderDao: OrderDao
    abstract val userDao: UserDao

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