package com.syntext.error.gitissue.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.syntext.error.gitissue.ui.screen.RepoSearchScreen
import com.syntext.error.gitissue.ui.screen.searchListScreen.SearchListScreen

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.RepoSearchScreen.route) {

        // Repo Search Screen
        composable(Screen.RepoSearchScreen.route) {
           RepoSearchScreen(
//                onNavigateToRepoList = {
//                    navController.navigate(Screen.RepoListScreen.route)
//                }
            )
        }

        // Repo List Screen
        composable(Screen.RepoListScreen.route) {
            SearchListScreen(
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


sealed class Screen(val route: String) {
    data object RepoSearchScreen : Screen("repo_search")
    data object RepoListScreen : Screen("repo_list")
    data object ProjectScreen : Screen("project")
    data object DetailsScreen : Screen("details")
    data object IssueScreen : Screen("issues")
}