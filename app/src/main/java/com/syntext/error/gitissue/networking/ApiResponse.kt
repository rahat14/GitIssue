package com.syntext.error.gitissue.networking

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val exception: Throwable, val message: String = exception.localizedMessage ?: "Unknown Error") : ApiResponse<Nothing>()
    data object Loading : ApiResponse<Nothing>()
}
