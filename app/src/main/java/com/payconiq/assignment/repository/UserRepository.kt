package com.payconiq.assignment.repository

import com.payconiq.assignment.network.ApiService
import com.payconiq.assignment.network.ResultWrapper
import com.payconiq.assignment.network.model.FindUserResponse
import com.payconiq.assignment.network.model.UserDetailsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class UserRepository(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRepository() {

    suspend fun findUsers(query: String?): ResultWrapper<FindUserResponse?> {
        return safeApiCall(dispatcher) {
            apiService.findUsers(query)
        }
    }

    suspend fun getUserDetails(userName: String?): ResultWrapper<UserDetailsResponse?> {
        return safeApiCall(dispatcher) {
            apiService.getUserDetails(userName)
        }
    }
}