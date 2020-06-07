package be.scoutswondelgem.wafelbak.repository

import be.scoutswondelgem.wafelbak.api.WafelbakApiClient
import be.scoutswondelgem.wafelbak.api.models.responses.AuthResponseModel
import io.reactivex.Single

class UserRepository(private val wafelbakApiClient: WafelbakApiClient) {
    fun login(email: String, password: String): Single<AuthResponseModel> {
        return wafelbakApiClient.login(email, password)
    }

    fun register(firstName: String, lastName: String, email: String, password: String, birthday: String, street: String,
                 streetNumber: Int, streetExtra: String?, postalCode: Int, city: String):Single<AuthResponseModel> {
        return wafelbakApiClient.register(firstName, lastName, email, password, birthday, street, streetNumber, streetExtra, postalCode, city)
    }

    fun isValidEmail(email: String, oldEmail: String?): Single<Boolean> {
        return wafelbakApiClient.isValidEmail(email, oldEmail)
    }
    /*
    fun editProfile(authToken:String, id: Int, firstName: String, lastName: String, email: String, password: String, birthday: String, street: String,
                    streetNumber: Int, streetExtra: String?, postalCode: Int, city: String):Single<User> {
        return wafelbakApiClient.editProfile(authToken, id, firstName, lastName, email, password, birthday, street, streetNumber, streetExtra, postalCode, city)
    }

    fun getUserByEmail(authToken: String, email: String): Single<User> {
        return wafelbakApiClient.getUserByEmail(authToken, email)
    }*/
}