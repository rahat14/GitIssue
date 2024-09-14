package com.syntext.error.gitissue.ui.screen.searchListScreen

sealed class SearchListAction {
    data object NavigateToProjectRepo : SearchListAction()
    data class SearchRepo(val query: String) : SearchListAction()
    data class LoadMore(val query: String, var page: Int) : SearchListAction()
}