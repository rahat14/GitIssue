package com.syntext.error.gitissue.data

class IssueResp : ArrayList<IssueResp.IssueItem>(){
    data class IssueItem(
        val active_lock_reason: Any? = null,
        val author_association: String? = null,
        val body: String? = null,
        val closed_at: Any? = null,
        val closed_by: Any? = null,
        val comments: Int? = null,
        val comments_url: String? = null,
        val created_at: String? = null,
        val events_url: String? = null,
        val html_url: String? = null,
        val id: Long? = null,
        val labels: List<Any?>? = null,
        val labels_url: String? = null,
        val locked: Boolean? = null,
        val milestone: Any? = null,
        val node_id: String? = null,
        val number: Int? = null,
        val repository_url: String? = null,
        val state: String? = null,
        val timeline_url: String? = null,
        val title: String? = null,
        val updated_at: String? = null,
        val url: String? = null,
        val user: User? = null
    ) {
    
        data class User(
            val avatar_url: String? = null,
            val events_url: String? = null,
            val followers_url: String? = null,
            val following_url: String? = null,
            val gists_url: String? = null,
            val gravatar_id: String? = null,
            val html_url: String? = null,
            val id: Int? = null,
            val login: String? = null,
            val node_id: String? = null,
            val organizations_url: String? = null,
            val received_events_url: String? = null,
            val repos_url: String? = null,
            val site_admin: Boolean? = null,
            val starred_url: String? = null,
            val subscriptions_url: String? = null,
            val type: String? = null,
            val url: String? = null
        )
    }
}