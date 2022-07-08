package com.surajrathod.authme.network

import com.surajrathod.authme.model.*
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

const val BASE_URL = "https://secret-dawn-66102.herokuapp.com/"
interface NetworkInterface {


    //User Auth

    @Headers("Content-Type: application/json")
    @POST("$BASE_URL/auth/register")
    suspend fun registerUser(@Body registerReq : RegisterReq) : SimpleResponse

    @Headers("Content-Type: application/json")
    @POST("$BASE_URL/auth/login")
    suspend fun loginUser(@Body loginReq: LoginReq) : LoginResponse

    @Headers("Content-Type: application/json")
    @POST("$BASE_URL/auth/otp")
    suspend fun sendOtp(@Query("email") email : String) : SimpleResponse

    @Headers("Content-Type: application/json")
    @POST("$BASE_URL/auth/resetpassword")
    suspend fun resetPassword(@Query("email") email: String, @Query("otp") otp : String, @Query("newpas") newpas : String) : SimpleResponse

    //user Update

    @Headers("Content-Type: application/json")
    @POST("$BASE_URL/user/update_profile")
    suspend fun updateUser(@Body user : User) : SimpleResponse


}