package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.*
import be.scoutswondelgem.wafelbak.models.Address

data class DBAddress(
    @ColumnInfo(name = "Street")
    var street: String,

    @ColumnInfo(name = "PostalCode")
    var postalCode: Int,

    @ColumnInfo(name = "City")
    var city: String
)

fun DBAddress.asDomainModel(): Address {
    return Address(
        street = this.street,
        postalCode = this.postalCode,
        city = this.city
        )
}

// Eventuele uitbereiding mogelijk om alle adressen op te slaan ==> entity maken hiervan en DAO
