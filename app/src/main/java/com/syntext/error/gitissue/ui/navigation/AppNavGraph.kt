package com.syntext.error.gitissue.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.syntext.error.gitissue.ui.screen.RepoSearchScreen
import com.syntext.error.gitissue.ui.screen.projectScreen.ProjectScreenContainer
import com.syntext.error.gitissue.ui.screen.searchListScreen.SearchListContainer
import com.syntext.error.gitissue.ui.shared.SharedViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val sharedViewModel: SharedViewModel = koinViewModel()

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
            val args = it.toRoute<Screen.SearchListContainer>()
            SearchListContainer(
                query = args.query,
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToProjectScreen = { repo ->
                    sharedViewModel.updateState(repo)
                    navController.navigate(Screen.ProjectScreenContainer) {
                        restoreState = true
                        launchSingleTop = true
                    }
                },
            )
        }

        // Project Screen with Bottom Navigation
        composable<Screen.ProjectScreenContainer> {
            ProjectScreenContainer(
                currentRepo = sharedViewModel.sharedState,
                onNavigateBack = {
                    navController.navigateUp()
                },
//                onNavigateToProject = {
//                    navController.navigate(Screen.ProjectScreen.route)
//                }
            )
        }

    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.koinSharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return getViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return getViewModel<T>(viewModelStoreOwner = parentEntry)
}


@Serializable
sealed class Screen {
    @Serializable
    data object RepoSearchScreen : Screen()

    @Serializable
    data class SearchListContainer(
        val query: String,
    ) : Screen()

    @Serializable
    data object ProjectScreenContainer : Screen()

    @Serializable
    data object DetailsScreen : Screen()

    @Serializable
    data object IssueScreen : Screen()

    @Serializable
    data class IssueDetailsScreen(
        val title: String,
        val body: String,
        val userName: String,
        val userImage: String,
    ) : Screen()
}