package ru.ifedorov.thousandcourses.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.ifedorov.account.AccountRoute
import ru.ifedorov.courses.CoursesRoute
import ru.ifedorov.favorites.FavoritesRoute

private const val MAIN_GRAPH_ROUTE = "main"

@Composable
fun ThousandCoursesNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = MAIN_GRAPH_ROUTE
    ) {
        navigation(
            startDestination = TopLevelDestination.Home.route,
            route = MAIN_GRAPH_ROUTE
        ) {
            composable(route = TopLevelDestination.Home.route) {
                CoursesRoute(
                    modifier = Modifier.padding(innerPadding),
                    onDetailsClick = {},
                    onFilterClick = {},
                )
            }
            composable(route = TopLevelDestination.Favorites.route) {
                FavoritesRoute(
                    modifier = Modifier.padding(innerPadding),
                    onDetailsClick = {}
                )
            }
            composable(route = TopLevelDestination.Account.route) {
                AccountRoute(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}
