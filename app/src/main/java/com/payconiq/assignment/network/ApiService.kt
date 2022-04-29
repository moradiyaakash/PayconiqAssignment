package com.payconiq.assignment.network

import com.payconiq.assignment.network.model.FindUserResponse
import com.payconiq.assignment.network.model.UserDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun findUsers(@Query("q") q: String?): Response<FindUserResponse>

    @GET("/users/{username}")
    suspend fun getUserDetails(@Path("username") username: String?): Response<UserDetailsResponse>
}