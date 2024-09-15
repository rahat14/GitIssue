package com.syntext.error.gitissue.repository

import com.syntext.error.gitissue.data.IssueResp
import com.syntext.error.gitissue.data.RepoReadMeResp
import com.syntext.error.gitissue.data.RepoSearchResp
import com.syntext.error.gitissue.networking.ApiResponse
import com.syntext.error.gitissue.networking.safeApiCall
import com.syntext.error.gitissue.services.GitApiService

class GithubRemoteRepositoryImpl(private val apiService: GitApiService) : GithubRemoteRepository {

    override suspend fun searchRepo(
        query: String,
        page: Int,
    ): ApiResponse<RepoSearchResp> {
        return safeApiCall {
            apiService.repoSearch(query = query, page = page)
        }
    }

    override suspend fun getRepoReadme(
        repoName: String,
        owner: String
    ): ApiResponse<RepoReadMeResp> {
        return safeApiCall {
            apiService.repoReadMe(repo = repoName, owner = owner)
        }
    }

    override suspend fun getRepoIssues(repoName: String, owner: String): ApiResponse<IssueResp> {
        return safeApiCall {
            apiService.repoIssues(repo = repoName, owner = owner)
        }
    }

    //  issue+repo:ollama/ollama
    override suspend fun searchRepoIssues(
        repoName: String,
        owner: String,
        query: String,
        page: Int
    ): ApiResponse<IssueResp> {
        return safeApiCall {
            apiService.searchRepoIssues(query = "$query+repo:$repoName/$owner" , page =  page)
        }
    }
}