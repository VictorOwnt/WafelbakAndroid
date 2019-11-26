package be.scoutswondelgem.wafelbak.injection.component

import be.scoutswondelgem.wafelbak.injection.module.NetworkModule
import be.scoutswondelgem.wafelbak.viewmodels.UserViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ViewModelInjectorComponent {

    fun inject(userViewModel: UserViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjectorComponent

        fun networkModule(networkModule: NetworkModule): Builder
    }

}