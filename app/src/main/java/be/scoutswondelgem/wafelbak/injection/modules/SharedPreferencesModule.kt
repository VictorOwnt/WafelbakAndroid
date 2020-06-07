package be.scoutswondelgem.wafelbak.injection.modules

import android.content.Context
import android.content.SharedPreferences
import be.scoutswondelgem.wafelbak.util.SharedPreferencesEnum
import org.koin.dsl.module.module

val sharedPreferencesModule = module {
    single { provideSharedPreferences(get()) }
}

fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(SharedPreferencesEnum.ID.string, Context.MODE_PRIVATE)
}
