package com.syntext.error.gitissue.di

import com.syntext.error.gitissue.networking.RetrofitInstance
import org.koin.dsl.module

val appModule = module {
    single { RetrofitInstance.api } // Provide the Retrofit API instance

}