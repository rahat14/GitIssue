package com.syntext.error.gitissue.ui.screen.projectScreen.screens.repoIssueList

import com.syntext.error.gitissue.data.Repo
import com.syntext.error.gitissue.ui.screen.projectScreen.screens.projectSummaryScreen.ProjectSummaryAction

sealed class RepoIssuesAction {
    data class GetInitialIssues(val currentRepo : Repo) : RepoIssuesAction()
    data class SearchIssues(val query: String) : RepoIssuesAction()
    data object LoadMoreIssues : RepoIssuesAction()
    data class LoadMoreSearchIssues(val query: String) : RepoIssuesAction()
    data object ToggleSearchOff : RepoIssuesAction()
    data object InitDoNothing : RepoIssuesAction()
}