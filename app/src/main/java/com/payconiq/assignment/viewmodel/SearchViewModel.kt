package com.payconiq.assignment.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payconiq.assignment.network.ApiClient
import com.payconiq.assignment.network.ResultWrapper
import com.payconiq.assignment.repository.SearchRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    var searchLiveData = MutableLiveData<ResultWrapper<String?>>()
    private val searchRepository = SearchRepository(ApiClient.apiService)
    private var searchJob: Job? = null

    fun onSearchTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        Log.e("@@@Searched Text ", "is $s")
        callSearchUserApi(text = s.toString())
    }

    private fun callSearchUserApi(text: String?) {
       // searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            searchLiveData.value = searchRepository.searchUsers(query = text)
        }
    }
}