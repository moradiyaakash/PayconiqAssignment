package com.payconiq.assignment.network

sealed class ResultWrapper<out T> {
    object Loading : ResultWrapper<Nothing>()
    data class Success<out T>(val response: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val message: String? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}