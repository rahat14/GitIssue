package com.syntext.error.gitissue.repository

import com.syntext.error.gitissue.data.RepoReadMeResp
import com.syntext.error.gitissue.data.RepoSearchResp
import com.syntext.error.gitissue.networking.ApiResponse


interface GithubRemoteRepository {
    suspend fun searchRepo(
        query: String,
        page: Int,
    ): ApiResponse<RepoSearchResp>


    suspend fun getRepoReadme(
        repoName: String,
        owner: String,
    ): ApiResponse<RepoReadMeResp>



}