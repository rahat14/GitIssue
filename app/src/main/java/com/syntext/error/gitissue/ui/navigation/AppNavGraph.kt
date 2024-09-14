package com.syntext.error.gitissue.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.syntext.error.gitissue.ui.screen.RepoSearchScreen
import com.syntext.error.gitissue.ui.screen.searchListScreen.SearchListScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.RepoSearchScreen) {

        // Repo Search Screen
        composable<Screen.RepoSearchScreen> {
            RepoSearchScreen(
                onNavigateToRepoList = { query ->
                    navController.navigate(Screen.RepoListScreen(query = query))
                },
            )
        }

        // Repo List Screen
        composable<Screen.RepoListScreen> {
            val args = it.toRoute<Screen.RepoListScreen>()
            SearchListScreen(
                query = args.query
//                onNavigateToProject = {
//                    navController.navigate(Screen.ProjectScreen.route)
//                }
            )
        }

        // Project Screen with Bottom Navigation
//        composable(Screen.ProjectScreen.route) {
//            ProjectScreen()
//        }
    }
}


@Serializable
sealed class Screen {
    @Serializable
    data object RepoSearchScreen : Screen()

    @Serializable
    data class RepoListScreen(
        val query: String,
    ) : Screen()

    data object ProjectScreen : Screen()
    data object DetailsScreen : Screen()
    data object IssueScreen : Screen()
}