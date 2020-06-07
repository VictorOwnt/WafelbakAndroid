package be.scoutswondelgem.wafelbak.api.adapters

import be.scoutswondelgem.wafelbak.api.models.enums.UserRole
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class UserRoleAdapter {
    @ToJson
    fun userRoleToJson(userRole: UserRole): String {
        return userRole.role
    }

    @FromJson
    fun fromJson(value: String?): UserRole? {
        when(value) {
            "admin" -> return UserRole.ADMIN
            "member" -> return UserRole.MEMBER
            "user" -> return  UserRole.USER
            else -> return UserRole.USER
        }
    }
}