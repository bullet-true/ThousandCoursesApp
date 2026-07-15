package ru.ifedorov.thousandcourses.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.ifedorov.account.AccountRoute
import ru.ifedorov.courses.CoursesRoute
import ru.ifedorov.favorites.FavoritesRoute

private const val MAIN_TABS_GRAPH_ROUTE = "main_tabs"

@Composable
fun ThousandCoursesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MAIN_TABS_GRAPH_ROUTE,
        modifier = modifier
    ) {
        navigation(
            startDestination = TopLevelDestination.Home.route,
            route = MAIN_TABS_GRAPH_ROUTE
        ) {
            composable(route = TopLevelDestination.Home.route) {
                CoursesRoute(
                    onDetailsClick = {},
                    onFilterClick = {},
                )
            }
            composable(route = TopLevelDestination.Favorites.route) {
                FavoritesRoute(
                    onDetailsClick = {}
                )
            }
            composable(route = TopLevelDestination.Account.route) {
                AccountRoute()
            }
        }
    }
}
