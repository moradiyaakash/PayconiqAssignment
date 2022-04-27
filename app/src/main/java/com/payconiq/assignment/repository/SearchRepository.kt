package com.payconiq.assignment.repository

import com.payconiq.assignment.network.ApiService
import com.payconiq.assignment.network.ResultWrapper
import com.payconiq.assignment.network.model.FindUserResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SearchRepository(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    BaseRepository() {

    suspend fun searchUsers(query: String?): ResultWrapper<FindUserResponse?> {
        return safeApiCall(dispatcher) {
            apiService.searchUsers(query)
        }
    }
}