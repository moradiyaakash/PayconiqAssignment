package com.payconiq.assignment.network

import com.google.gson.annotations.SerializedName

data class ErrorWrapper(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)
