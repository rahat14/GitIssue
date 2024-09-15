package com.syntext.error.gitissue.ui.screen.projectScreen.screens.projectSummaryScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntext.error.gitissue.networking.ApiResponse
import com.syntext.error.gitissue.repository.GithubRemoteRepository
import com.syntext.error.gitissue.utils.decodeBase64ToString
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProjectSummaryViewmodel(
    private val gitRepo: GithubRemoteRepository,
) : ViewModel() {


    private val _state: MutableStateFlow<ProjectSummaryScreenState> =
        MutableStateFlow(ProjectSummaryScreenState())
    val state: StateFlow<ProjectSummaryScreenState> get() = _state

    private val _actions: Channel<ProjectSummaryEvent> = Channel(Channel.BUFFERED)
    val actions: Flow<ProjectSummaryEvent> get() = _actions.receiveAsFlow()


    fun postActions(action: ProjectSummaryAction) = viewModelScope.launch {

        when (action) {

            is ProjectSummaryAction.ConvertReadme -> {
                val temp = decodeBase64ToString(action.encryptedReadme)

                updateReadme(data = temp)

            }

            is ProjectSummaryAction.GetReadme -> {
                _state.update {

                    it.copy(isLoading = true)
                }

                val response =
                    gitRepo.getRepoReadme(repoName = action.repoName, owner = action.owner)

                when (response) {

                    is ApiResponse.Success -> {

                        val tempData = response.data.content

                        decryptReadme(data = tempData ?: "", sha = response.data.sha ?: "")

                    }

                    is ApiResponse.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                    }


                }

            }

            is ProjectSummaryAction.UpdateReadme -> {
                _state.update {
                    it.copy(isLoading = false , readme = action.readme)
                }

            }
        }


    }


    private fun updateReadme(data: String) {

        postActions(
            ProjectSummaryAction.UpdateReadme(
                data
            )
        )
    }


    private fun decryptReadme(data: String, sha: String) {

        postActions(
            ProjectSummaryAction.ConvertReadme(
                data, sha
            )
        )
    }


}