package com.syntext.error.gitissue.ui.screen.projectScreen.screens.repoIssueList

import com.syntext.error.gitissue.data.IssueResp
import com.syntext.error.gitissue.data.Repo


data class RepoIssuesListState(
    var isEmpty: Boolean = false,
    var issueList: List<IssueResp.IssueItem> = emptyList(),
    var currentSearchList: List<IssueResp.IssueItem> = emptyList(),
    var isLoading: Boolean = false,
    var errorMessage: String? = null,
    var isLoadMore: Boolean = false,
    var currentQuery: String? = null,
    var isSearchOn: Boolean = false,
    var currentRepo: Repo? = null
)