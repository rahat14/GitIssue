package com.syntext.error.gitissue.ui.screen.projectScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.Coil
import com.syntext.error.gitissue.data.Repo
import com.syntext.error.gitissue.ui.navigation.Screen
import com.syntext.error.gitissue.ui.screen.projectScreen.screens.ProjectIssueListScreen
import com.syntext.error.gitissue.ui.screen.projectScreen.screens.ProjectSummaryScreen
import com.syntext.error.gitissue.ui.theme.GitIssueTheme
import dev.jeziellago.compose.markdowntext.MarkdownText


@Composable
fun ProjectScreenContainer(currentRepo: Repo?) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = { ProjectBottomNavigation(navController) }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        ProjectNavHost(navController, currentRepo, modifier)
    }


}


@Composable
fun ProjectBottomNavigation(navController: NavHostController) {
    NavigationBar {
        val items = listOf(BottomNavItem.Details, BottomNavItem.Issues)

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = "") },
                label = { Text(text = screen.route) },
                selected = false, // You can handle selection state properly here
                onClick = {
                    navController.navigate(screen.screen)
                }
            )
        }
    }
}


@Composable
fun ProjectNavHost(
    navController: NavHostController,
    currentRepo: Repo?,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.DetailsScreen,
        modifier = modifier
    ) {

        // Details Screen
        composable<Screen.DetailsScreen> {
            ProjectSummaryScreen(currentRepo)
        }

        // Issue Screen
        composable<Screen.IssueScreen> {
            ProjectIssueListScreen(currentRepo)
        }
    }
}


@Composable
fun IssueDetailsScreen() {


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProjectSummaryScreenPreview() {
    GitIssueTheme {
        ProjectSummaryScreen(null)
    }

}

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String,
    val screen: Screen
) {
    data object Details :
        BottomNavItem("details", Icons.Default.Home, "Details", Screen.DetailsScreen)

    data object Issues : BottomNavItem("issues", Icons.Default.Info, "Issues", Screen.IssueScreen)
}


@Composable
fun MarkDownViewer(modifier: Modifier = Modifier , markdownText : String) {
    val ctx = LocalContext.current
    MarkdownText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        markdown = markdownText,
        //   fontResource = R.font.montserrat_medium,
        style = TextStyle(
            color = Color.White,
            fontSize = 14.sp,
        ),

        imageLoader = Coil.imageLoader(ctx)

    )


}