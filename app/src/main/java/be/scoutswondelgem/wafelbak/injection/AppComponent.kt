package be.scoutswondelgem.wafelbak.injection

import be.scoutswondelgem.wafelbak.injection.modules.networkModule
import org.koin.dsl.module.Module

val appComponent: List<Module> = listOf(networkModule /*viewModelModule, repositoryModule, sharedPreferencesModule*/)
