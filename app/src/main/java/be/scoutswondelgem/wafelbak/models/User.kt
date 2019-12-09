package be.scoutswondelgem.wafelbak.models

import java.util.*

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val isAdmin: Boolean = false,
    val birthday: Date,
    val address: Address,
    val imgUrl: String? = null,
    val orders: MutableList<Order> = mutableListOf(),
    val token: String? = null
)