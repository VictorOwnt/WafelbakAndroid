package be.scoutswondelgem.wafelbak.models

data class Address(
    val street: String,
    val streetNumber: Int,
    val streetExtra: String?,
    val postalCode: Int,
    val city: String
)