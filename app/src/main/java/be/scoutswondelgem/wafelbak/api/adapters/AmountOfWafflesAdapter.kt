package be.scoutswondelgem.wafelbak.api.adapters

import be.scoutswondelgem.wafelbak.api.models.enums.AmountOfWaffles
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class AmountOfWafflesAdapter {
    @ToJson
    fun amountOfWafflesToJson(amountOfWaffles: AmountOfWaffles): String {
        return amountOfWaffles.amount.toString()
    }

    @FromJson
    fun fromJson(value: String): AmountOfWaffles? {
        when(value) {
            "2" -> return AmountOfWaffles.TWO
            "4" -> return AmountOfWaffles.FOUR
            "8" -> return AmountOfWaffles.EIGHT
            "20" -> return AmountOfWaffles.TWENTY
            else -> return AmountOfWaffles.FOUR
        }
    }
}