package com.syntext.error.gitissue.data

data class RepoSearchResp(
    val incomplete_results: Boolean? = null,
    val items: List<Repo?>? = emptyList(),
    val total_count: Int? = null
) {

}