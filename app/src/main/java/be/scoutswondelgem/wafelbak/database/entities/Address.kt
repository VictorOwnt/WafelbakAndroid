package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.*

data class Address(
    @ColumnInfo(name = "Street")
    val street: String,

    @ColumnInfo(name = "PostalCode")
    val postalCode: Int,

    @ColumnInfo(name = "City")
    val city: String
)
