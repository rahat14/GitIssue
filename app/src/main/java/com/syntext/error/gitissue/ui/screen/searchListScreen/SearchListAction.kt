package com.syntext.error.gitissue.ui.screen.searchListScreen

import com.syntext.error.gitissue.data.Repo

sealed class SearchListAction {
    data class NavigateToProjectRepo(val repo : Repo) : SearchListAction()
    data class SearchRepo(val query: String) : SearchListAction()
    data class LoadMore(val query: String) : SearchListAction()
    data object NavigateToSearchScreen : SearchListAction()
}