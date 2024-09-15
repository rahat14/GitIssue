package com.syntext.error.gitissue.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.syntext.error.gitissue.ui.screen.ProjectScreen
import com.syntext.error.gitissue.ui.screen.RepoSearchScreen
import com.syntext.error.gitissue.ui.screen.searchListScreen.SearchListContainer
import com.syntext.error.gitissue.ui.shared.SharedViewModel
import kotlinx.serialization.Serializable

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screen.RepoSearchScreen) {

        // Repo Search Screen
        composable<Screen.RepoSearchScreen> {
            RepoSearchScreen(
                onNavigateToRepoList = { query ->
                    navController.navigate(Screen.SearchListContainer(query = query))
                },
            )
        }

        // Repo List Screen
        composable<Screen.SearchListContainer> {
            val viewModel = it.sharedViewModel<SharedViewModel>(navController)
            val args = it.toRoute<Screen.SearchListContainer>()
            SearchListContainer(
                query = args.query,
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToProjectScreen = { repo ->
                    viewModel.updateState(repo)
                    navController.navigate(Screen.ProjectScreen)
                }
            )
        }

        // Project Screen with Bottom Navigation
        composable<Screen.ProjectScreen> {
            val viewModel = it.sharedViewModel<SharedViewModel>(navController)
            val state by viewModel.sharedState.collectAsStateWithLifecycle()

            val args = it.toRoute<Screen.ProjectScreen>()

            ProjectScreen(
                currentRepo = state
//                onNavigateToProject = {
//                    navController.navigate(Screen.ProjectScreen.route)
//                }
            )
        }

    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}


@Serializable
sealed class Screen {
    @Serializable
    data object RepoSearchScreen : Screen()

    @Serializable
    data class SearchListContainer(
        val query: String,
    ) : Screen()

    data object ProjectScreen : Screen()
    data object DetailsScreen : Screen()
    data object IssueScreen : Screen()
}