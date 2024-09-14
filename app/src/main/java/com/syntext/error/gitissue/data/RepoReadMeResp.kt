package com.syntext.error.gitissue.data

data class RepoReadMeResp(
    val _links: Links? = null,
    val content: String? = null,
    val download_url: String? = null,
    val encoding: String? = null,
    val git_url: String? = null,
    val html_url: String? = null,
    val name: String? = null,
    val path: String? = null,
    val sha: String? = null,
    val size: Int? = null,
    val type: String? = null,
    val url: String? = null
) {
    data class Links(
        val git: String? = null,
        val html: String? = null,
        val self: String? = null
    )
}