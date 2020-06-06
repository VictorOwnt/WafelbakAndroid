package be.scoutswondelgem.wafelbak.injection.modules

import android.content.Context
import be.scoutswondelgem.wafelbak.database.WafelbakDatabase
import be.scoutswondelgem.wafelbak.database.daos.OrderDao
import be.scoutswondelgem.wafelbak.database.daos.StreetDao
import be.scoutswondelgem.wafelbak.database.daos.UserDao
import be.scoutswondelgem.wafelbak.database.daos.ZoneDao
import org.koin.dsl.module.module

val databaseModule = module {
    single { provideDatabase(get()) }
    factory { provideOrderDao(get()) }
    factory { provideStreetDao(get()) }
    factory { provideUserDao(get()) }
    factory { provideZoneDao(get()) }
}

fun provideDatabase(applicationContext: Context): WafelbakDatabase {
    return WafelbakDatabase.getInstance(applicationContext)
}
fun provideOrderDao(wafelbakDatabase: WafelbakDatabase): OrderDao {
    return wafelbakDatabase.orderDao
}

fun provideStreetDao(wafelbakDatabase: WafelbakDatabase): StreetDao {
    return wafelbakDatabase.streetDao
}

fun provideUserDao(wafelbakDatabase: WafelbakDatabase): UserDao {
    return wafelbakDatabase.userDao
}

fun provideZoneDao(wafelbakDatabase: WafelbakDatabase): ZoneDao {
    return wafelbakDatabase.zoneDao
}
