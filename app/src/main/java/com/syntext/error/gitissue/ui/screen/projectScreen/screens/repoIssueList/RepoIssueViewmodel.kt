package com.syntext.error.gitissue.ui.screen.projectScreen.screens.repoIssueList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntext.error.gitissue.networking.ApiResponse
import com.syntext.error.gitissue.repository.GithubRemoteRepository
import com.syntext.error.gitissue.ui.screen.projectScreen.screens.projectSummaryScreen.ProjectSummaryEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RepoIssueViewmodel(
    private val gitRepo: GithubRemoteRepository,
) : ViewModel() {

    private var initialPage = 1

    private val _state: MutableStateFlow<RepoIssuesListState> =
        MutableStateFlow(RepoIssuesListState())
    val state: StateFlow<RepoIssuesListState> get() = _state

    private val _actions: Channel<ProjectSummaryEvent> = Channel(Channel.BUFFERED)
    val actions: Flow<ProjectSummaryEvent> get() = _actions.receiveAsFlow()


    fun postActions(action: RepoIssuesAction) = viewModelScope.launch {

        when (action) {
            is RepoIssuesAction.GetInitialIssues -> {

                Log.d("TAG", "postActions: i ma ere  ")

                if (action.currentRepo.id == _state.value.currentRepo?.id) {
                    return@launch
                }

                _state.update {
                    it.copy(isLoading = true, currentRepo = action.currentRepo, isSearchOn = false)
                }


                val response = gitRepo.getRepoIssues(
                    action.currentRepo.name ?: "",
                    action.currentRepo.owner?.login ?: ""
                )

                when (response) {
                    is ApiResponse.Success -> {
                        _state.update {
                            it.copy(
                                issueList = response.data,
                                isLoading = false,
                                isEmpty = response.data.isEmpty()
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


                }

            }

            is RepoIssuesAction.LoadMoreIssues -> {
                initialPage += 1

                _state.update {
                    it.copy(isLoadMore = true, isSearchOn = false)
                }

                val response = gitRepo.getRepoIssues(
                    _state.value.currentRepo?.name ?: "",
                    _state.value.currentRepo?.owner?.login ?: ""
                )

                when (response) {
                    is ApiResponse.Success -> {
                        _state.update {
                            it.copy(
                                issueList = it.issueList + response.data,
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

                }


            }

            is RepoIssuesAction.LoadMoreSearchIssues -> {

                initialPage += 1

                _state.update {
                    it.copy(isLoadMore = true, isSearchOn = true, isLoading = false)
                }


                val response = gitRepo.searchRepoIssues(
                    _state.value.currentRepo?.name ?: "",
                    _state.value.currentRepo?.owner?.login ?: "", action.query,
                    initialPage
                )

                when (response) {
                    is ApiResponse.Success -> {
                        _state.update {
                            it.copy(
                                currentSearchList = it.currentSearchList + (response.data.items
                                    ?: emptyList()),
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


                }

            }

            is RepoIssuesAction.SearchIssues -> {
                initialPage = 1

                _state.update {
                    it.copy(isLoading = true, isSearchOn = true, isLoadMore = false)
                }


                val response = gitRepo.searchRepoIssues(
                    _state.value.currentRepo?.name ?: "",
                    _state.value.currentRepo?.owner?.login ?: "", action.query,
                    initialPage
                )

                when (response) {
                    is ApiResponse.Success -> {
                        _state.update {
                            it.copy(
                                currentSearchList = response.data.items ?: emptyList(),
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

                }

            }

            is RepoIssuesAction.ToggleSearchOff -> {
                _state.update {
                    it.copy(isSearchOn = false, currentSearchList = emptyList())
                }
            }
        }
    }
}