package com.syntext.error.gitissue.services

import com.syntext.error.gitissue.data.IssueResp
import com.syntext.error.gitissue.data.RepoReadMeResp
import com.syntext.error.gitissue.data.RepoSearchResp
import com.syntext.error.gitissue.data.SearchIssueResp
import kotlinx.serialization.json.Json
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface GitApiService {
    @GET("/search/repositories")
    suspend fun repoSearch(
        @Query("q") query: String,
        @Query("page") page: Int = 1 ,
        @Query("per_page") perPage: Int = 16
    ): Response<RepoSearchResp>


    @GET("/repos/{owner}/{repo}/readme")
    suspend fun repoReadMe(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
    ): Response<RepoReadMeResp>


    @GET("/repos/{owner}/{repo}/issues")
    suspend fun repoIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int = 1 ,
        @Query("per_page") perPage: Int = 16
    ): Response<IssueResp>


//https://api.github.com/search/issues?q=issue+repo:ollama/ollama
    @GET
    suspend fun searchRepoIssues(
             @Url url: String
//        @Query("q") query: String,
//        @Query("page") page: Int = 1 ,
//        @Query("per_page") perPage: Int = 16
    ): Response<SearchIssueResp>


}

