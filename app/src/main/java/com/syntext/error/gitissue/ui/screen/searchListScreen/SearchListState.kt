package com.syntext.error.gitissue.ui.screen.searchListScreen

import com.syntext.error.gitissue.data.Repo


data class SearchListState(
    val isEmpty: Boolean = false,
    val searchList: List<Repo> = emptyList(),
    val isLoading: Boolean = false,

    )