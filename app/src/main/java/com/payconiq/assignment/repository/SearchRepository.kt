package com.payconiq.assignment.repository

import com.payconiq.assignment.network.ApiService
import com.payconiq.assignment.network.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SearchRepository(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    BaseRepository() {

    suspend fun searchUsers(query: String?): ResultWrapper<String?> {
        return safeApiCall(dispatcher) {
            apiService.searchUsers(query)
        }
    }
}