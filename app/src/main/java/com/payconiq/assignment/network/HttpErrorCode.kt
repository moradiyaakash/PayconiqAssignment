package com.payconiq.assignment.network

enum class HttpErrorCode(val code: Int) {
    /**
     * error in getting value (Json Error, Server Error, etc)
     */
    BAD_RESPONSE(500),
    /**
     * Time out error
     */
    TIMEOUT(408),
    /**
     * Http failed error
     */
    HTTP_FAILED(409)
}