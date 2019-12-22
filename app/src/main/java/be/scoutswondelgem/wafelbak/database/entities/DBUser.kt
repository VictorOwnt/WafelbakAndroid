package be.scoutswondelgem.wafelbak.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import be.scoutswondelgem.wafelbak.models.Address
import java.util.Date

@Entity(tableName = "Users")
data class DbUser(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="UserId")
    val userId: Long = 0L,
    @ColumnInfo(name="Voornaam")
    val firstName: String,
    @ColumnInfo(name="Achternaam")
    val lastName: String,
    @ColumnInfo(name="Email")
    val email: String,
    @Embedded
    val address: Address,
    @ColumnInfo(name="Geboortedatum")
    val birthday: Date,
    /*
    @ColumnInfo(name="AfbeeldingUrl")  // willen we afbeelding opslaan?
    val imgUrl: String? = null,*/       // Salt en hash ? ==> nee? inloggen moet via internet?
    @ColumnInfo(name="Admin")
    val isAdmin: Boolean = false
)

