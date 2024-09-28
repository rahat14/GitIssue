package com.syntext.error.gitissue.repository

import com.syntext.error.gitissue.data.Repo
import com.syntext.error.gitissue.data.RepoSearchResp
import com.syntext.error.gitissue.networking.ApiResponse
import com.syntext.error.gitissue.services.GitApiService
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class GithubRemoteRepositoryImplTest {

    private lateinit var apiService: GitApiService
    private lateinit var userRepository: GithubRemoteRepositoryImpl

    @BeforeEach
    fun setup() {
        apiService = mockk() // mocking api service
        // Create an instance of the repository with the mocked ApiService
        userRepository = GithubRemoteRepositoryImpl(apiService)
    }


    @Test
    fun `search repo  returns user when API is successful`() = runTest {
        val mockResp = mockk<Response<RepoSearchResp>>()
        val repoSearchResp = RepoSearchResp(
            items =  listOf(
                Repo(
                    id = 1,
                    name = "repo1",
                    full_name = "repo1",
                ),
                Repo(
                    id = 3,
                    name = "repo1",
                    full_name = "repo1",
                )
            )
        ) // demo success response

        // Mocking isSuccessful and body() methods
        coEvery { mockResp.isSuccessful } returns true
        coEvery { mockResp.body() } returns repoSearchResp

        // Set up the mocked API response
        coEvery { apiService.repoSearch("node", page = 1) } returns mockResp

        // Test repository method
        val result: ApiResponse<RepoSearchResp> = userRepository.searchRepo("node", page = 1)

        // Verify that the result is successful and contains the correct user
        Assertions.assertTrue(result is ApiResponse.Success)
        Assertions.assertEquals(repoSearchResp, (result as ApiResponse.Success).data)
    }

    @Test
    fun `search repo  returns error When API fails`() = runTest {
        val mockResponse = mockk<Response<RepoSearchResp>>()
        // demo fail response
        // Mocking failed API response
        coEvery { mockResponse.isSuccessful } returns false
        coEvery { mockResponse.code() } returns 404

        // Mock API call
        coEvery { apiService.repoSearch("test-query") } returns mockResponse

        // Test repository method
        val result = userRepository.searchRepo("test-query" , page = 1)

        // Assertions
        Assertions.assertTrue(result is ApiResponse.Error)
        Assertions.assertEquals(
            "Resource not found",
            (result as ApiResponse.Error).exception.message
        )
    }

    @Test
    fun `searchRepos returns Error on exception`() = runTest {
        // Mock API call to throw an exception
        coEvery { apiService.repoSearch("test-query") } throws Exception("Unknown Error")

        // Test repository method
        val result = userRepository.searchRepo("test-query" , page = 1)

        // Assertions
        assertTrue(result is ApiResponse.Error)
        assertEquals("Unknown Error", (result as ApiResponse.Error).exception.message)
    }

}