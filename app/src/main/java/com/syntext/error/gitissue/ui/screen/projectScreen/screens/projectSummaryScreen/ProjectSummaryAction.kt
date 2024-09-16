package com.syntext.error.gitissue.ui.screen.projectScreen.screens.projectSummaryScreen


sealed class ProjectSummaryAction {
    data class GetReadme(val repoName: String, val owner: String) : ProjectSummaryAction()
    data class ConvertReadme(val encryptedReadme: String, val sha : String ) : ProjectSummaryAction()
    data class UpdateReadme(val readme: String) : ProjectSummaryAction()
    data object InitDoNothing : ProjectSummaryAction()
}