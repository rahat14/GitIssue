package com.syntext.error.gitissue.ui.screen.projectScreen.bottomNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.syntext.error.gitissue.ui.navigation.Screen

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