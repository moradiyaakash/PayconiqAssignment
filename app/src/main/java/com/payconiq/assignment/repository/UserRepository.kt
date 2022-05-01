package com.payconiq.assignment.repository

import com.payconiq.assignment.network.ApiService
import com.payconiq.assignment.network.ResultWrapper
import com.payconiq.assignment.network.model.FindUserResponse
import com.payconiq.assignment.network.model.UserDetailsResponse

class UserRepository(
    private val apiService: ApiService
) : BaseRepository() {

    suspend fun findUsers(query: String?): ResultWrapper<FindUserResponse?> {
        return safeApiCall {
            apiService.findUsers(query)
        }
    }

    suspend fun getUserDetails(userName: String?): ResultWrapper<UserDetailsResponse?> {
        return safeApiCall {
            apiService.getUserDetails(userName)
        }
    }
}