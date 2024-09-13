package com.syntext.error.gitissue.services

import kotlinx.serialization.json.Json
import retrofit2.Response
import retrofit2.http.GET

interface GitApiService {
    @GET("endpoint")
    suspend fun repoSearch(): Response<Json>
}