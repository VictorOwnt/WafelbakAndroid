package be.scoutswondelgem.wafelbak.api

import be.scoutswondelgem.wafelbak.models.Order
import be.scoutswondelgem.wafelbak.models.OrderAndUser
import be.scoutswondelgem.wafelbak.models.User
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface WafelbakApi {

    /**
     * Gets all users
     *
     *
     * @return list of all users
     */
    @GET("users/")
    fun getUsers(): Observable<List<User>>

    /**
     * Gets user by id
     *
     * @param id
     * @return user
     */
    @GET("users/id/{id}")
    fun getUserById(@Path("id") id: Int): Single<User>

    /**
     * Gets user by email
     *
     * @param authToken
     * @param email
     * @return user
     */
    @GET("users/{email}")
    fun getUserByEmail(@Header("Authorization") authToken: String, @Path("email") email: String): Single<User>

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
    ): Single<User>

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
                 @Field("city") city: String): Single<User>

    /**
     * Edit user
     *
     * @param authToken
     * @param id
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
     * @return user
     */
    @FormUrlEncoded
    @PATCH("users/editProfile")
    fun editProfile(@Header("Authorization") authToken: String,
                    @Field("id") id: Int,
                    @Field("firstName") firstName: String,
                    @Field("lastName") lastName: String,
                    @Field("email") email: String,
                    @Field("password") password: String,
                    @Field("birthday") birthday: String,
                    @Field("street") street: String,
                    @Field("streetNumber") streetNumber: Int,
                    @Field("streetExtra") streetExtra: String?,
                    @Field("postalCode") postalCode: Int,
                    @Field("city") city: String): Single<User>

    /**
     * Gets order by id
     *
     * @param authToken
     * @param id
     * @return order
     */
    @GET("orders/id/{id}")
    fun getOrderById(@Header("Authorization") authToken: String, @Path("id") id: Int): Single<Order>

    /**
     * Gets orders by userId
     *
     * @param authToken
     * @param userId
     * @return order
     */
    @GET("orders/byUserId/{userId}")
    fun getOrdersByUserId(@Header("Authorization") authToken: String, @Path("userId") userId: Int): Observable<List<Order>>

    /**
     * Gets orders joined by users
     *
     * @param authToken
     */
    @GET("orders/joined")
    fun getOrdersJoined(@Header("Authorization") authToken: String): Observable<List<OrderAndUser>>

    /**
     * Gets orders by userEmail
     *
     * @param email
     * @return order
     */
    @GET("orders/byUserMail/{email}")
    fun getOrdersByUserEmail(@Path("email") email: Int): Observable<List<Order>>

    /**
     * Creates order
     *
     * @param authToken
     * @param amountOfWaffles
     * @param desiredDeliveryTime
     * @param comment
     * @param userid
     * @return order
     */
    @FormUrlEncoded
    @POST("orders/create")
    fun createOrder(@Header("Authorization") authToken: String,
                    @Field("amountOfWaffles") amountOfWaffles: Int,
                    @Field("desiredDeliveryTime") desiredDeliveryTime: String,
                    @Field("comment") comment: String,
                    @Field("userid") userid: Int): Single<Order>

    /**
     * Updates order
     *
     * @param authToken
     * @param order
     * @return order
     */
    @PATCH("orders/patch")
    fun updateOrder(@Header("Authorization") authToken: String, @Body order: Order) : Single<Order>

    /**
     * Deletes order
     *
     * @param authToken
     * @param order
     * @return order
     */
    @HTTP(method = "DELETE", path = "orders/delete", hasBody = true)
    fun deleteOrder(@Header("Authorization") authToken: String, @Body order: Order) : Single<Order>

}