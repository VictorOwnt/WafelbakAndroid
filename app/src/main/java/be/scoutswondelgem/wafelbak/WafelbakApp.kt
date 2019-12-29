package be.scoutswondelgem.wafelbak

import android.app.Application
import org.koin.android.ext.android.startKoin
import be.scoutswondelgem.wafelbak.injection.appComponent

class WafelbakApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, appComponent)
    }
}