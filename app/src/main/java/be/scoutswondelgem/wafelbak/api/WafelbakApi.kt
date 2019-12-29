package be.scoutswondelgem.wafelbak.api

import be.scoutswondelgem.wafelbak.models.Order
import be.scoutswondelgem.wafelbak.models.User
import retrofit2.http.*

interface WafelbakApi {

    /**
     * Gets all users
     *
     *
     * @return list of all users
     */
    @GET("users/")
    suspend fun getUsers(): List<User>

    /**
     * Gets user by id
     *
     * @param id
     * @return user
     */
    @GET("users/id/{id}")
    suspend fun getUserById(@Path("id") id: Int): User

    /**
     * Gets user by email
     *
     * @param email
     * @return user
     */
    @GET("users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): User

    /**
     * Checks is email is valid and unique
     *
     * @param email
     * @return true if valid
     */
    @FormUrlEncoded
    @POST("users/isValidEmail")
    suspend fun isValidEmail(@Field("email") email: String): Boolean

    /**
     * Signs in existing users
     *
     * @param email
     * @param password
     * @return User with token
     */
    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): User

    /**
     * Gets all orders
     *
     *
     * @return list of all orders
     */
    @GET("orders/")
    suspend fun getOrders(): List<Order>

    /**
     * Gets order by id
     *
     * @param id
     * @return order
     */
    @GET("orders/id/{id}")
    suspend fun getOrderById(@Path("id") id: Int): Order

    /**
     * Gets order by userId
     *
     * @param userId
     * @return order
     */
    @GET("orders/byUserId/{id}")
    suspend fun getOrderByUserId(@Path("userId") userId: Int): Order

    /**
     * Gets order by userEmail
     *
     * @param email
     * @return order
     */
    @GET("orders/byUserMail/{email}")
    suspend fun getOrderByUserEmail(@Path("email") email: Int): Order

    /**
     * Creates order
     *
     * @param amountOfWaffles
     * @param desiredDeliveryTime
     * @param comment
     * @param userid
     * @return order
     */
    @POST("orders/create")
    suspend fun createOrder(@Field("amountOfWaffles") amountOfWaffles: Int,
                    @Field("desiredDeliveryTime") desiredDeliveryTime: String,
                    @Field("comment") comment: String,
                    @Field("userid") userid: Int): Order

}