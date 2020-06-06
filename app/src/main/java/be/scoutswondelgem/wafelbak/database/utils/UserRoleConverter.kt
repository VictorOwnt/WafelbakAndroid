package be.scoutswondelgem.wafelbak.database.utils

import androidx.room.TypeConverter
import be.scoutswondelgem.wafelbak.models.enums.UserRole

class UserRoleConverter {
    @TypeConverter
    fun userRoleToDatabase(userRole: UserRole): String {
        return userRole.role
    }

    @TypeConverter
    fun fromDatabaseToUserRole(value: String?): UserRole? {
        when(value) {
            "admin" -> return UserRole.ADMIN
            "member" -> return UserRole.MEMBER
            "user" -> return  UserRole.USER
            else -> return UserRole.USER
        }
    }
}