package be.scoutswondelgem.wafelbak.injection.modules

import be.scoutswondelgem.wafelbak.api.WafelbakApiClient
import be.scoutswondelgem.wafelbak.repository.OrderRepository
import be.scoutswondelgem.wafelbak.repository.UserRepository
import org.koin.dsl.module.module


val repositoryModule = module {
    factory { provideUserRepository(get()) }
    factory { provideOrderRepository(get()) }
}

fun provideUserRepository(apiClient: WafelbakApiClient): UserRepository {
    return UserRepository(apiClient)
}

fun provideOrderRepository(apiClient: WafelbakApiClient): OrderRepository {
    return OrderRepository(apiClient)
}

