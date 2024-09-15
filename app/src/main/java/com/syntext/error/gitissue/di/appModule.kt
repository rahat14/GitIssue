package com.syntext.error.gitissue.di

import com.syntext.error.gitissue.networking.RetrofitInstance
import com.syntext.error.gitissue.repository.GithubRemoteRepository
import com.syntext.error.gitissue.repository.GithubRemoteRepositoryImpl
import com.syntext.error.gitissue.ui.screen.searchListScreen.SearchListViewmodel
import com.syntext.error.gitissue.ui.shared.SharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RetrofitInstance.api } // Provide the Retrofit API instance

    // Add other Koin modules or dependencies here
    single<GithubRemoteRepository> { GithubRemoteRepositoryImpl(get()) }

    // Provide ViewModel
    viewModel { SearchListViewmodel(get()) }
    viewModel { SharedViewModel() }
}