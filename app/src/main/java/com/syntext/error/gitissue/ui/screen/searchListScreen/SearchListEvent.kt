package com.syntext.error.gitissue.ui.screen.searchListScreen

import com.syntext.error.gitissue.data.Repo

sealed  interface SearchListEvent {

    data class NavigateToProjectRepo(val repo : Repo) : SearchListEvent
    data object NavigateBack : SearchListEvent
    data class ShowMsg(val message : String ) : SearchListEvent
    data object DoNothing : SearchListEvent
}