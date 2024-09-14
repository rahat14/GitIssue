package com.syntext.error.gitissue.networking

import com.syntext.error.gitissue.const.baseURL
import com.syntext.error.gitissue.services.GitApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    val api: GitApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseURL)
            .client(HttpClient.provideClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitApiService::class.java)
    }
}
