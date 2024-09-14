package com.syntext.error.gitissue.ui.screen.searchListScreen

import com.syntext.error.gitissue.data.Repo


data class SearchListState(
    var isEmpty: Boolean = false,
    var searchList: List<Repo> = emptyList(),
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
    var isLoadMore : Boolean = false
    )