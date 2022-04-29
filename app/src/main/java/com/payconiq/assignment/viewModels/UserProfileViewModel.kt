package com.payconiq.assignment.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payconiq.assignment.network.ApiClient
import com.payconiq.assignment.network.ResultWrapper
import com.payconiq.assignment.network.model.UserDetailsResponse
import com.payconiq.assignment.repository.UserRepository
import kotlinx.coroutines.launch

class UserProfileViewModel : ViewModel() {

    var userName: String? = null

    private val _userDetails = MutableLiveData<ResultWrapper<UserDetailsResponse?>>()
    val userDetails: LiveData<ResultWrapper<UserDetailsResponse?>>
        get() = _userDetails

    private val userRepository = UserRepository(ApiClient.apiService)

    fun callGetUserDetailsApi() {
        viewModelScope.launch {
            _userDetails.value = ResultWrapper.Loading
            _userDetails.value = userRepository.getUserDetails(userName = userName)
        }
    }
}