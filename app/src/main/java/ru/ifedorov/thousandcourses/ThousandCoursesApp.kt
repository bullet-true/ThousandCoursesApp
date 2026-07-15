package ru.ifedorov.thousandcourses

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.ifedorov.auth.LoginRoute
import ru.ifedorov.thousandcourses.ui.navigation.BottomBar
import ru.ifedorov.thousandcourses.ui.navigation.ThousandCoursesNavHost
import ru.ifedorov.thousandcourses.ui.navigation.TopLevelDestination
import ru.ifedorov.ui.theme.ThousandCoursesTheme

private const val ROOT_LOGIN_ROUTE = "root_login"
private const val ROOT_MAIN_ROUTE = "root_main"

@Composable
fun ThousandCoursesApp() {
    ThousandCoursesTheme {
        val rootNavController = rememberNavController()

        NavHost(
            navController = rootNavController,
            startDestination = ROOT_LOGIN_ROUTE
        ) {
            composable(route = ROOT_LOGIN_ROUTE) {
                LoginRoute(
                    onLoginSuccess = {
                        rootNavController.navigate(ROOT_MAIN_ROUTE) {
                            popUpTo(ROOT_LOGIN_ROUTE) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(route = ROOT_MAIN_ROUTE) {
                MainAppScaffold()
            }
        }
    }
}

@Composable
private fun MainAppScaffold() {
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
            modifier = Modifier.padding(innerPadding)
        )
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
