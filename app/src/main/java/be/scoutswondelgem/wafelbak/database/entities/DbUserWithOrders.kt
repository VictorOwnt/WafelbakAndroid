package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class DbUserWithOrders(
    @Embedded
    val user: DbUser,
    @Relation(
        parentColumn = "UserId",
        entityColumn = "FK_UserId",
        entity = DbOrder::class
    )
    val orders: List<DbOrder>
)