package be.scoutswondelgem.wafelbak.models


enum class DeliveryDate(val levertijd: String) {
    VOORMIDDAG("Voor 12u"),
    NAMIDDAG("Tussen 13u en 16u"),
    AVOND("Na 16u"),
    MAAKTNIETUIT("Het maakt niet uit")
}