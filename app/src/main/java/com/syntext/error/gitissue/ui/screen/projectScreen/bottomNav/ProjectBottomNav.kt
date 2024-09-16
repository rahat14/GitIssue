package com.syntext.error.gitissue.ui.screen.projectScreen.bottomNav

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

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