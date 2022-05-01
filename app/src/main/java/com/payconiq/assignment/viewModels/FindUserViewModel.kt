package com.payconiq.assignment.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payconiq.assignment.network.ApiClient
import com.payconiq.assignment.network.ResultWrapper
import com.payconiq.assignment.network.model.FindUserResponse
import com.payconiq.assignment.repository.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FindUserViewModel : ViewModel() {

    private val _searchResults = MutableLiveData<ResultWrapper<FindUserResponse?>>()
    val searchResults: LiveData<ResultWrapper<FindUserResponse?>>
        get() = _searchResults

    private val userRepository = UserRepository(ApiClient.apiService)

    private var searchJob: Job? = null

    fun callFindUserApi(text: String?) {
        cancelJob()
        searchJob = viewModelScope.launch {
            delay(500)
            _searchResults.value = ResultWrapper.Loading
            _searchResults.value = userRepository.findUsers(query = text)
        }
    }

    fun cancelJob(){
        searchJob?.cancel()
    }
}