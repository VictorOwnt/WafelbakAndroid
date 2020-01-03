package be.scoutswondelgem.wafelbak.api

import be.scoutswondelgem.wafelbak.models.Order
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
     * @param email
     * @return user
     */
    @GET("users/{email}")
    fun getUserByEmail(@Path("email") email: String): Single<User>

    /**
     * Checks is email is valid and unique
     *
     * @param email
     * @return true if valid
     */
    @FormUrlEncoded
    @POST("users/isValidEmail")
    fun isValidEmail(@Field("email") email: String): Single<Boolean>

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
     * Gets all orders
     *
     *
     * @return list of all orders
     */
    @GET("orders/")
    fun getOrders(): Observable<List<Order>>

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
     * @param amountOfWaffles
     * @param desiredDeliveryTime
     * @param comment
     * @param userid
     * @return order
     */
    @FormUrlEncoded
    @POST("orders/create")
    fun createOrder(@Field("amountOfWaffles") amountOfWaffles: Int,
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
    @PATCH("orders/id/patch")
    fun updateOrder(@Header("Authorization") authToken: String, @Body order: Order) : Single<Array<Int>>

}