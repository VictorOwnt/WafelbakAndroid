package be.scoutswondelgem.wafelbak.database

import android.content.Context
import androidx.room.*
import be.scoutswondelgem.wafelbak.database.dataAccessObjects.*
import be.scoutswondelgem.wafelbak.database.entities.*
import be.scoutswondelgem.wafelbak.util.Converters

@Database(version = 1, entities = [User::class, Order::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class WafelbakDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun orderDao(): OrderDao

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
                        "wafelbak_database"
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