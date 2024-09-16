package com.syntext.error.gitissue.ui.screen.projectScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.syntext.error.gitissue.data.Repo
import com.syntext.error.gitissue.ui.navigation.Screen
import com.syntext.error.gitissue.ui.screen.projectScreen.bottomNav.ProjectBottomNavigation
import com.syntext.error.gitissue.ui.screen.projectScreen.screens.IssueDetailsScreen
import com.syntext.error.gitissue.ui.screen.projectScreen.screens.projectSummaryScreen.ProjectSummaryScreen
import com.syntext.error.gitissue.ui.screen.projectScreen.screens.repoIssueList.ProjectIssueListScreen


@Composable
fun ProjectScreenContainer(
    currentRepo: Repo?,
    onNavigateBack: () -> Boolean
) {

    val navController = rememberNavController()
    Scaffold(
        bottomBar = { ProjectBottomNavigation(navController) }
    ) { innerPadding ->
        val modifier =
            Modifier.padding(PaddingValues(bottom = innerPadding.calculateBottomPadding()))
        ProjectNavHost(
            navController = navController,
            currentRepo = currentRepo,
            modifier = modifier,
        ) {
            onNavigateBack()
        }
    }


}


@Composable
fun ProjectNavHost(
    navController: NavHostController,
    currentRepo: Repo?,
    modifier: Modifier,
    onNavigateBack: () -> Boolean
) {
    NavHost(
        navController = navController,
        startDestination = Screen.DetailsScreen,
        modifier = modifier
    ) {

        // Details Screen
        composable<Screen.DetailsScreen> {
            ProjectSummaryScreen(currentRepo) {
                onNavigateBack()
            }
        }

        // Issue Screen
        composable<Screen.IssueScreen> {
            ProjectIssueListScreen(currentRepo, onNavigateBack = onNavigateBack,
                onNavigateToIssueDetails = { title, body, userName, userImage ->
                    navController.navigate(
                        Screen.IssueDetailsScreen(
                            title = title,
                            body,
                            userName,
                            userImage
                        )
                    ) {
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<Screen.IssueDetailsScreen> {
            val args = it.toRoute<Screen.IssueDetailsScreen>()

            IssueDetailsScreen(
                title = args.title,
                body = args.body,
                userName = args.userName,
                userImage = args.userImage,
                onNavigateBack = {
                    navController.navigateUp()
                }

            )
        }
    }
}




