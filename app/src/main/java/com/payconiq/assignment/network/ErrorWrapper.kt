package com.rentazon.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ErrorWrapper {

    @SerializedName("Status")
    @Expose
    var status: Boolean? = false

    @SerializedName("Code")
    @Expose
    var code: Int? = 0

    @SerializedName("Message")
    @Expose
    var message: String? = null
}
