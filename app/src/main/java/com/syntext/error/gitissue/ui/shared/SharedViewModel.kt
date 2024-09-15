package com.syntext.error.gitissue.ui.shared

import androidx.lifecycle.ViewModel
import com.syntext.error.gitissue.data.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel(
) : ViewModel() {

     var  sharedState : Repo? =  null

    fun updateState(repo: Repo) {
        sharedState = repo
        println("ViewModel named ${repo.name}")
    }

    override fun onCleared() {
        super.onCleared()
        println("ViewModel cleared")
    }
}