package be.scoutswondelgem.wafelbak.util

enum class SharedPreferencesEnum(val string: String) {
    PREFNAME("USER_CREDENTIALS"),
    ID("ID"),
    EMAIL("EMAIL"),
    FIRSTNAME("FIRSTNAME"),
    LASTNAME("LASTNAME"),
    // IMGURL("IMGURL"),
    ADMIN("ADMIN"),
    TOKEN("TOKEN"),
    ISLOGGEDIN("ISLOGGEDIN")
}