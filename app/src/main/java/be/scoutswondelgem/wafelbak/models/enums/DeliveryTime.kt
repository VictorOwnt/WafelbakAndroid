package be.scoutswondelgem.wafelbak.models.enums


enum class DeliveryTime(val deliveryTime: String) {
    MORNING("9u-12u"),
    AFTERNOON("13u-16u"),
    EVENING("16u-18u"),
    DOESNTMATTER("Om het even")
}