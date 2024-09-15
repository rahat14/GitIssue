package com.syntext.error.gitissue.ui.screen.projectScreen.screens.repoIssueList

sealed  interface RepoIssueEvent {
    data object NavigateBack : RepoIssueEvent
}