package com.syntext.error.gitissue.ui.screen.searchListScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class SearchListViewmodel(get: Any) : ViewModel() {

    private val _state: MutableStateFlow<SearchListState> = MutableStateFlow(SearchListState())
    val state: StateFlow<SearchListState> get() = _state

    private val _actions: Channel<SearchListEvent> = Channel(Channel.BUFFERED)
    val actions: Flow<SearchListEvent> get() = _actions.receiveAsFlow()




}