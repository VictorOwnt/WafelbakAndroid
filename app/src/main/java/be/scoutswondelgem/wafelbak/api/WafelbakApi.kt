package be.scoutswondelgem.wafelbak.api

import be.scoutswondelgem.wafelbak.models.Order
import be.scoutswondelgem.wafelbak.models.User
import retrofit2.Call
import retrofit2.http.*

interface WafelbakApi {

    /**
     * Gets all users
     *
     *
     * @return list of all users
     */
    @GET("users/")
    fun getUsers(): Call<List<User>>

    /**
     * Gets user by id
     *
     * @param id
     * @return user
     */
    @GET("users/id/{id}")
    fun getUserById(@Path("id") id: Integer): Call<User>

    /**
     * Gets user by email
     *
     * @param email
     * @return user
     */
    @GET("users/{email}")
    fun getUserByEmail(@Path("email") email: String): Call<User>

    /**
     * Checks is email is valid and unique
     *
     * @param email
     * @return true if valid
     */
    @FormUrlEncoded
    @POST("users/isValidEmail")
    fun isValidEmail(@Field("email") email: String): Call<Boolean>

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
    ): Call<User>

    /**
     * Gets all orders
     *
     *
     * @return list of all orders
     */
    @GET("orders/")
    fun getOrders(): Call<List<Order>>

    /**
     * Gets order by id
     *
     * @param id
     * @return order
     */
    @GET("orders/id/{id}")
    fun getOrderById(@Path("id") id: Integer): Call<Order>

    /**
     * Gets order by userId
     *
     * @param userId
     * @return order
     */
    @GET("orders/byUserId/{id}")
    fun getOrderByUserId(@Path("userId") userId: Integer): Call<Order>

    /**
     * Gets order by userEmail
     *
     * @param email
     * @return order
     */
    @GET("orders/byUserMail/{email}")
    fun getOrderByUserEmail(@Path("email") email: Integer): Call<Order>

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
    fun createOrder(@Field("amountOfWaffles") amountOfWaffles: Integer,
                    @Field("desiredDeliveryTime") desiredDeliveryTime: String,
                    @Field("comment") comment: String,
                    @Field("userid") userid: Integer): Call<Order>

}