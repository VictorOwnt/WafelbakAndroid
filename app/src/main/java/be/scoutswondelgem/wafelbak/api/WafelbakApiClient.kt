package be.scoutswondelgem.wafelbak.api

import be.scoutswondelgem.wafelbak.api.models.responses.AuthResponseModel
import be.scoutswondelgem.wafelbak.api.models.responses.OrderResponseModel
import io.reactivex.Single
import retrofit2.http.*

interface WafelbakApiClient {

    /**
     * Checks is email is valid and unique
     *
     * @param email
     * @param oldEmail
     * @return true if valid
     */
    @FormUrlEncoded
    @POST("users/isValidEmail")
    fun isValidEmail(@Field("email") email: String, @Field("oldEmail") oldEmail: String?): Single<Boolean>

    /**
     * Signs in existing users
     *
     * @param email
     * @param password
     * @return User with token
     */
    @FormUrlEncoded
    @POST("users/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<AuthResponseModel>

    /**
     * Registers user
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param birthday
     * @param street
     * @param streetNumber
     * @param streetExtra
     * @param postalCode
     * @param city
     * @return order
     */
    @FormUrlEncoded
    @POST("users/register")
    fun register(@Field("firstName") firstName: String,
                 @Field("lastName") lastName: String,
                 @Field("email") email: String,
                 @Field("password") password: String,
                 @Field("birthday") birthday: String,
                 @Field("street") street: String,
                 @Field("streetNumber") streetNumber: Int,
                 @Field("streetExtra") streetExtra: String?,
                 @Field("postalCode") postalCode: Int,
                 @Field("city") city: String): Single<AuthResponseModel>

    /**
     * Gets orders
     *
     * @param authToken
     * @return order
     */
    @GET("orders")
    fun getOrders(@Header("Authorization") authToken: String): Single<List<OrderResponseModel>>

}