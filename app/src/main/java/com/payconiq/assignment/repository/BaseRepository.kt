package com.payconiq.assignment.repository

import android.util.Log
import com.payconiq.assignment.network.HttpErrorCode
import com.payconiq.assignment.network.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeoutException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(
        dispatcher: CoroutineDispatcher,
        call: suspend () -> Response<T>
    ): ResultWrapper<T?> {
        return withContext(dispatcher) {
            try {
                val response = call.invoke()
                Log.e("@@@COMES HERE....", "$response")
                ResultWrapper.Success(response.body())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        ResultWrapper.GenericError(HttpErrorCode.HTTP_FAILED.code, null)
                    }
                    is TimeoutException -> {
                        ResultWrapper.GenericError(HttpErrorCode.TIMEOUT.code, throwable.message)
                    }
                    else -> {
                        ResultWrapper.GenericError(
                            HttpErrorCode.BAD_RESPONSE.code,
                            throwable.message
                        )
                    }
                }
            }
        }
    }
}