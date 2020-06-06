package be.scoutswondelgem.wafelbak.database

import android.content.Context
import androidx.room.*
import be.scoutswondelgem.wafelbak.database.daos.OrderDao
import be.scoutswondelgem.wafelbak.database.daos.StreetDao
import be.scoutswondelgem.wafelbak.database.daos.UserDao
import be.scoutswondelgem.wafelbak.database.daos.ZoneDao
import be.scoutswondelgem.wafelbak.database.entities.*
import be.scoutswondelgem.wafelbak.database.utils.*

@Database(entities = [AddressDataModel::class, CityDataModel::class, OrderDataModel::class, StreetDataModel::class, UserDataModel::class, ZoneDataModel::class], version = 1, exportSchema = false)
@TypeConverters(AmountOfWafflesConverter::class, DateConverter::class, DeliveryStatusConverter::class, DeliveryTimeConverter::class, UserRoleConverter::class)
abstract class WafelbakDatabase : RoomDatabase() {

    abstract val orderDao: OrderDao
    abstract val streetDao: StreetDao
    abstract val userDao: UserDao
    abstract val zoneDao: ZoneDao

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
                        "wafelbak_db"
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