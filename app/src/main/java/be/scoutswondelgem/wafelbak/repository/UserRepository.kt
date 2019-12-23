package be.scoutswondelgem.wafelbak.repository

import be.scoutswondelgem.wafelbak.api.WafelbakApi
import org.koin.dsl.module.module

val userModule = module {
    factory { UserRepository(get()) }
}

class UserRepository(private val wafelbakApi: WafelbakApi) {
    fun login(email: String, password: String) = wafelbakApi.login(email, password)
    fun isValidEmail(email: String) = wafelbakApi.isValidEmail(email)
    // suspend fun getUsers() = wafelbakApi.getUsers()
    // suspend fun getUserByEmail(email: String) = wafelbakApi.getUserByEmail(email)
}