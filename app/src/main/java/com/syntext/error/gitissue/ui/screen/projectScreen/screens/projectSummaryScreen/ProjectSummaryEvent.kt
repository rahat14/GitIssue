package com.syntext.error.gitissue.ui.screen.projectScreen.screens.projectSummaryScreen

import com.syntext.error.gitissue.data.Repo

sealed  interface ProjectSummaryEvent {
    data object NavigateBack : ProjectSummaryEvent
}