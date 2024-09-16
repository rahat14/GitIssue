package com.syntext.error.gitissue.ui.screen.projectScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.syntext.error.gitissue.data.Repo
import com.syntext.error.gitissue.ui.navigation.Screen
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
fun ProjectBottomNavigation(navController: NavHostController) {
    NavigationBar(
        modifier = Modifier,
        windowInsets = WindowInsets(bottom = 4.dp, top = 2.dp),
        containerColor = Color(0xff161616).copy(alpha = 0.84f),
    ) {
        var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

        val items = listOf(BottomNavItem.Details, BottomNavItem.Issues)

        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                modifier = Modifier.defaultMinSize(minHeight = 56.dp),
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            screen.selectedIcon
                        } else {
                            screen.unSelectedIcon
                        }, contentDescription = "",
                        tint = if (selectedItemIndex == index) {
                            Color(0xff0A84FF)
                        } else {
                            Color(0xff0A84FF).copy(0.5f)
                        }
                    )
                },
                label = {
                    Text(
                        text = screen.label, color = if (selectedItemIndex == index) {
                            Color(0xff0A84FF)
                        } else {
                            Color(0xff0A84FF).copy(0.5f)
                        }
                    )
                },
                selected = selectedItemIndex == index, // You can handle selection state properly here
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(screen.screen) {
                        restoreState = true
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
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


sealed class BottomNavItem(
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val label: String,
    val screen: Screen
) {
    data object Details : BottomNavItem(
        "details",
        Icons.Filled.Home,
        Icons.Outlined.Home,
        "Details",
        Screen.DetailsScreen
    )

    data object Issues : BottomNavItem(
        "issues",
        Icons.Filled.Info,
        Icons.Outlined.Info,
        "Issues",
        Screen.IssueScreen
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProjectScreenContainerPreview() {


}

