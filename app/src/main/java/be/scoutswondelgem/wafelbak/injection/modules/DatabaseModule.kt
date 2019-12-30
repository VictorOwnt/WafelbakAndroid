package be.scoutswondelgem.wafelbak.injection.modules

import android.content.Context
import be.scoutswondelgem.wafelbak.database.WafelbakDatabase
import be.scoutswondelgem.wafelbak.database.daos.OrderDao
import be.scoutswondelgem.wafelbak.database.daos.UserDao
import org.koin.dsl.module.module

val databaseModule = module {
    single { provideDatabase(get()) }
    factory { provideUserDao(get()) }
    factory { provideOrderDao(get()) }
}

fun provideDatabase(applicationContext: Context): WafelbakDatabase {
    return WafelbakDatabase.getInstance(applicationContext)
}

fun provideUserDao(wafelbakDatabase: WafelbakDatabase): UserDao {
    return wafelbakDatabase.userDao
}


fun provideOrderDao(wafelbakDatabase: WafelbakDatabase): OrderDao {
    return wafelbakDatabase.orderDao
}