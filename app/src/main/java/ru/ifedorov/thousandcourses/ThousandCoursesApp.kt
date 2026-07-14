package ru.ifedorov.thousandcourses

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.ifedorov.thousandcourses.ui.navigation.BottomBar
import ru.ifedorov.thousandcourses.ui.navigation.ThousandCoursesNavHost
import ru.ifedorov.thousandcourses.ui.navigation.TopLevelDestination
import ru.ifedorov.ui.theme.ThousandCoursesTheme

@Composable
fun ThousandCoursesApp() {
    ThousandCoursesTheme {
        val navController = rememberNavController()
        val currentBackStackEntry = navController.currentBackStackEntryAsState().value
        val selectedTab = TopLevelDestination.fromRoute(currentBackStackEntry?.destination?.route)

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                BottomBar(
                    selectedTab = selectedTab,
                    onTabClick = { tab -> navController.navigateToTab(tab) }
                )
            }
        ) { innerPadding ->
            ThousandCoursesNavHost(
                navController = navController,
                innerPadding = innerPadding
            )
        }
    }
}

private fun NavHostController.navigateToTab(tab: TopLevelDestination) {
    navigate(tab.route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
    }
}
