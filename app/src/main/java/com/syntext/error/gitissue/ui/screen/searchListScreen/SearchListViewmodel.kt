package com.syntext.error.gitissue.ui.screen.searchListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntext.error.gitissue.networking.ApiResponse
import com.syntext.error.gitissue.repository.GithubRemoteRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchListViewmodel(
    private val gitRepo: GithubRemoteRepository,
) : ViewModel() {

    var searchPage = 1

    private val _state: MutableStateFlow<SearchListState> = MutableStateFlow(SearchListState())
    val state: StateFlow<SearchListState> get() = _state

    private val _actions: Channel<SearchListEvent> = Channel(Channel.BUFFERED)
    val actions: Flow<SearchListEvent> get() = _actions.receiveAsFlow()


    fun postActions(action: SearchListAction) = viewModelScope.launch {

        when (action) {
            is SearchListAction.LoadMore -> {
                _state.update {
                    it.copy(isLoadMore = true, isLoading = false)
                }

                searchPage += 1 // increasing page

                val response = gitRepo.searchRepo(query = action.query, page = searchPage)

                when (response) {
                    ///TODO detect end

                    is ApiResponse.Success -> {
                        _state.update {
                            it.copy(
                                searchList = it.searchList + (response.data.items ?: emptyList()),
                                isLoadMore = false
                            )
                        }
                    }

                    is ApiResponse.Error -> {
                        _state.update {
                            it.copy(
                                errorMessage = response.message,
                                isLoadMore = false
                            )
                        }
                    }

                    ApiResponse.Loading -> {
                        _state.update { it.copy(isLoadMore = true) }
                    }


                }

            }
            is SearchListAction.NavigateToProjectRepo -> {

                _actions.trySend(SearchListEvent.NavigateToProjectRepo(action.repo))
            }
            is SearchListAction.SearchRepo -> {

                _state.update {
                    it.copy(isLoading = true)
                }

                searchPage = 1
                val response = gitRepo.searchRepo(query = action.query, page = searchPage)


                when (response) {
                    is ApiResponse.Success -> {
                        _state.update {
                            it.copy(
                                searchList = response.data.items ?: emptyList(),
                                isLoading = false,
                                isEmpty = response.data.items.isNullOrEmpty()
                            )
                        }
                    }

                    is ApiResponse.Error -> {
                        _state.update {
                            it.copy(
                                errorMessage = response.message,
                                isLoading = false
                            )
                        }
                    }

                    ApiResponse.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }


                }
            }
            SearchListAction.NavigateToSearchScreen -> {

                _actions.trySend(SearchListEvent.NavigateBack)

            }
        }

    }

}