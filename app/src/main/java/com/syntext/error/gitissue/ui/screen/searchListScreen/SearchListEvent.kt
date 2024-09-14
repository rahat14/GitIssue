package com.syntext.error.gitissue.ui.screen.searchListScreen

interface SearchListEvent {

    data object NavigateToProjectRepo : SearchListEvent
    data object NavigateBack : SearchListEvent
}