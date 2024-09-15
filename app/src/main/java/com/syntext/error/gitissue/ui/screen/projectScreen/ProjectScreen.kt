package com.syntext.error.gitissue.ui.screen.projectScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.Coil
import com.syntext.error.gitissue.R
import com.syntext.error.gitissue.common.EmptySpace
import com.syntext.error.gitissue.data.Repo
import com.syntext.error.gitissue.ui.navigation.Screen
import com.syntext.error.gitissue.ui.theme.GitIssueTheme
import com.syntext.error.gitissue.ui.theme.Orange
import com.syntext.error.gitissue.ui.theme.TextColorGray
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
fun ProjectSummaryScreen(currentRepo: Repo?) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.onBackground
            )
    ) {
        val modifier = Modifier.padding(horizontal = 8.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {

            EmptySpace(4)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(vertical = 4.dp)
            ) {

                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    "User Name",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = TextColorGray
                )

            }

            Text(
                currentRepo?.name ?: "N?A",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                ),
                modifier = modifier.padding(vertical = 8.dp)
            )



            Text(
                currentRepo?.description ?: "N?A",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                    letterSpacing = 1.5.sp
                ),
                modifier = modifier
            )

            EmptySpace(12)

            Row(modifier = modifier) {
                Icon(
                    Icons.Rounded.Share,
                    contentDescription = "share icon",
                    modifier = Modifier.size(16.dp),
                    tint = Orange
                )


                EmptySpace(2)


                Text(
                    currentRepo?.html_url ?: "N?A",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White,
                        fontSize = 12.sp,
                        letterSpacing = 1.5.sp
                    ),
                )


            }

            EmptySpace(4)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(vertical = 4.dp)
            ) {

                Icon(
                    Icons.Rounded.Star,
                    contentDescription = "Star",
                    modifier = Modifier.size(16.dp),
                    tint = Orange
                )

                EmptySpace(6)

                Text(
                    currentRepo?.stargazers_count.toString() ?: "N?A",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )

                Text(
                    "Stars",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    modifier = Modifier.padding(horizontal = 6.dp),
                    color = Color.White.copy(alpha = 0.7f)
                )

                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Color.Cyan)
                ) {}

                EmptySpace(6)

                Text(
                    currentRepo?.forks_count.toString() ?: "N?A",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )

                Text(
                    "Forks",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                    modifier = Modifier.padding(horizontal = 6.dp),
                    color = Color.White.copy(alpha = 0.7f)
                )


            }

            HorizontalDivider(
                thickness = 1.dp,
                color = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Text(
                "README.md",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                    letterSpacing = 1.5.sp
                ),
                modifier = modifier
            )

            MarkDownViewer()
        }


    }


}

@Composable
fun ProjectIssueListScreen(currentRepo: Repo?) {

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
fun MarkDownViewer(modifier: Modifier = Modifier) {
    val markdown =
        "### What is the issue?\n\nI have set the system parameters, but when loading the embedding model, only one is still in effect. I copied this model, and surprisingly, their model IDs are the same. After importing the model, the model ID changed, but the same model was still used in the end。\r\n![image](https://github.com/user-attachments/assets/e331defe-5548-479b-a5de-32a3d8d1de4d)\r\n\r\n\n\n### OS\n\nWindows\n\n### GPU\n\nNvidia\n\n### CPU\n\nIntel\n\n### Ollama version\n\n0.3.10"

    val ctx = LocalContext.current
    MarkdownText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        markdown = markdown,
        //   fontResource = R.font.montserrat_medium,
        style = TextStyle(
            color = Color.White,
            fontSize = 12.sp,
        ),

        imageLoader = Coil.imageLoader(ctx)

    )


}