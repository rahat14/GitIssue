package com.syntext.error.gitissue.ui.shared

import androidx.lifecycle.ViewModel
import com.syntext.error.gitissue.data.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel(
) : ViewModel() {

    private val _sharedState = MutableStateFlow<Repo?>(null)
    val sharedState = _sharedState.asStateFlow()

    fun updateState(repo: Repo) {
        _sharedState.value = repo
    }

    override fun onCleared() {
        super.onCleared()
        println("ViewModel cleared")
    }
}