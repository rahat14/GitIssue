package com.syntext.error.gitissue.ui.screen.projectScreen.screens.projectSummaryScreen

import com.syntext.error.gitissue.data.Repo
import com.syntext.error.gitissue.ui.screen.searchListScreen.SearchListEvent

sealed  interface ProjectSummaryEvent {
    data object DoNothing : ProjectSummaryEvent
}