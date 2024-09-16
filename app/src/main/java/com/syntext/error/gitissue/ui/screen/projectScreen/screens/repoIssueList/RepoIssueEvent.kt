package com.syntext.error.gitissue.ui.screen.projectScreen.screens.repoIssueList

import com.syntext.error.gitissue.ui.screen.projectScreen.screens.projectSummaryScreen.ProjectSummaryEvent

sealed  interface RepoIssueEvent {
    data object DoNothing : RepoIssueEvent
}