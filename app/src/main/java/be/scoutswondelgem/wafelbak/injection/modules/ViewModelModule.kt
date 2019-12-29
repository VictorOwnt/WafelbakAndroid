package be.scoutswondelgem.wafelbak.injection.modules

import be.scoutswondelgem.wafelbak.viewmodels.UserViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
    //viewModel { DayViewModel(get()) }
}