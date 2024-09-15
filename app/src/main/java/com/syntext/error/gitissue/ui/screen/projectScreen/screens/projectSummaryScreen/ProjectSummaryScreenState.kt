package com.syntext.error.gitissue.ui.screen.projectScreen.screens.projectSummaryScreen

import com.syntext.error.gitissue.data.Repo


data class ProjectSummaryScreenState(
    var readme : String = "",
    var isLoading: Boolean = false,
)