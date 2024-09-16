package com.syntext.error.gitissue.data

data class SearchIssueResp(
    val incomplete_results: Boolean? = null,
    val items: List<IssueResp.IssueItem>? = emptyList(),
    val total_count: Int? = null
) {

}