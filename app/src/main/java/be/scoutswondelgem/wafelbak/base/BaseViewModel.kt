package be.scoutswondelgem.wafelbak.base

import androidx.lifecycle.ViewModel
import be.scoutswondelgem.wafelbak.injection.component.DaggerViewModelInjectorComponent
import be.scoutswondelgem.wafelbak.injection.component.ViewModelInjectorComponent
import be.scoutswondelgem.wafelbak.injection.module.NetworkModule
import be.scoutswondelgem.wafelbak.viewmodels.UserViewModel

abstract class BaseViewModel : ViewModel() {

    private val injectorComponent: ViewModelInjectorComponent =
        DaggerViewModelInjectorComponent
            .builder()
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is UserViewModel -> injectorComponent.inject(this)
        }
    }

}