package be.scoutswondelgem.wafelbak.database.utils

import androidx.room.TypeConverter
import be.scoutswondelgem.wafelbak.models.enums.AmountOfWaffles

class AmountOfWafflesConverter {
    @TypeConverter
    fun amountOfWafflesToDatabase(amountOfWaffles: AmountOfWaffles): Int {
        return amountOfWaffles.amount
    }

    @TypeConverter
    fun fromDatabaseToAmountOfWaffles(value: Int): AmountOfWaffles? {
        when(value) {
            2 -> return AmountOfWaffles.TWO
            4 -> return AmountOfWaffles.FOUR
            8 -> return AmountOfWaffles.EIGHT
            20 -> return AmountOfWaffles.TWENTY
            else -> return AmountOfWaffles.FOUR
        }
    }
}