package be.scoutswondelgem.wafelbak.injection.modules

import be.scoutswondelgem.wafelbak.api.WafelbakApi
import be.scoutswondelgem.wafelbak.database.daos.UserDao
import be.scoutswondelgem.wafelbak.repository.UserRepository
import org.koin.dsl.module.module


val repositoryModule = module {
    factory { provideUserRepository(get(), get()) }
}

fun provideUserRepository(api: WafelbakApi, dao: UserDao): UserRepository {
    return UserRepository(api, dao)
}

