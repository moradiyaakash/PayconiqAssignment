package com.payconiq.assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payconiq.assignment.network.ApiClient
import com.payconiq.assignment.network.ResultWrapper
import com.payconiq.assignment.network.model.FindUserResponse
import com.payconiq.assignment.repository.SearchRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    var searchResponse = MutableLiveData<ResultWrapper<FindUserResponse?>>()
    private val searchRepository = SearchRepository(ApiClient.apiService)
    private var searchJob: Job? = null

    fun callFindUserApi(text: String?) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            searchResponse.value = searchRepository.searchUsers(query = text)
        }
    }
}