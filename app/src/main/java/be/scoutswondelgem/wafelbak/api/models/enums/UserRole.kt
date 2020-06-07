package be.scoutswondelgem.wafelbak.api.models.enums

enum class UserRole(val role: String) {
    ADMIN("admin"),
    MEMBER("member"),
    USER("user")
}