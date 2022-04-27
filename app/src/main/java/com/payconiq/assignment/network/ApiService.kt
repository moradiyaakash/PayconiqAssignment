package com.payconiq.assignment.network

import com.payconiq.assignment.network.model.FindUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun searchUsers(@Query("q") q: String?): Response<FindUserResponse>
}