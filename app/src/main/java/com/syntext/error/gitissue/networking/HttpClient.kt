package com.syntext.error.gitissue.networking

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class HttpClient {



    companion object {

        fun provideClient() : OkHttpClient {

            val httpClient =  OkHttpClient.Builder()

            httpClient.addInterceptor { chain ->
                val request: Request =
                    chain.request().newBuilder().also {
                        it.addHeader("Accept", "application/json")
                        it.addHeader("User-Agent", "Git-Issue")
                        //it.addHeader( "Authorization", "Git-Issue")
                    }

                        .build()
                chain.proceed(request)
            }


            httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))


            return  httpClient.build()

        }

    }

}
