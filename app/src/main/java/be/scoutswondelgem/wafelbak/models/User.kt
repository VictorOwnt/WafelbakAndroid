package be.scoutswondelgem.wafelbak.models

import java.util.*

data class User(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val address: Address,
    val birthday: Date,
    // val imgUrl: String? = null,
    val isAdmin: Boolean = false,
    val orders: MutableList<Order> = mutableListOf(),
    val token: String? = null
)