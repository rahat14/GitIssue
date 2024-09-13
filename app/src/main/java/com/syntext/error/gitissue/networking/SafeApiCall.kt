package com.syntext.error.gitissue.networking

import retrofit2.Response

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResponse<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                ApiResponse.Success(it)
            } ?: ApiResponse.Error(Throwable("Empty response body"))
        } else {
            when (response.code()) {
                404 -> ApiResponse.Error(Throwable("Resource not found"))
                500 -> ApiResponse.Error(Throwable("Server error"))
                else -> ApiResponse.Error(Throwable("Error: ${response.code()} ${response.message()}"))
            }
        }
    } catch (e: Exception) {
        when (e) {
            is java.net.SocketTimeoutException -> ApiResponse.Error(e, "Request timed out")
            is java.net.UnknownHostException -> ApiResponse.Error(e, "No internet connection")
            else -> ApiResponse.Error(e, e.localizedMessage ?: "Unknown error")
        }
    }
}