package ru.ifedorov.thousandcourses.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.ifedorov.account.AccountRoute
import ru.ifedorov.courses.CoursesRoute
import ru.ifedorov.favorites.FavoritesRoute
import ru.ifedorov.favorites.FavoritesUiState
import ru.ifedorov.favorites.FavoritesViewModel
import ru.ifedorov.thousandcourses.R
import ru.ifedorov.ui.component.ErrorContent
import ru.ifedorov.ui.component.LoadingContent

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
                    modifier = Modifier.padding(innerPadding)
                )
            }
            composable(route = TopLevelDestination.Favorites.route) {
                val viewModel = hiltViewModel<FavoritesViewModel>()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                FavoritesStateRoute(
                    uiState = uiState,
                    innerPadding = innerPadding,
                    onFavoriteClick = { courseId ->
                        viewModel.onFavoriteClick(courseId)
                    }
                )
            }
            composable(route = TopLevelDestination.Account.route) {
                AccountRoute(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
private fun FavoritesStateRoute(
    uiState: FavoritesUiState,
    innerPadding: PaddingValues,
    onFavoriteClick: (courseId: Int) -> Unit
) {
    when {
        uiState.isLoading -> LoadingContent(innerPadding = innerPadding)

        uiState.errorMessage != null -> ErrorContent(
            message = stringResource(id = R.string.courses_loading_error),
            innerPadding = innerPadding
        )

        else -> {
            FavoritesRoute(
                courses = uiState.courses,
                modifier = Modifier.padding(innerPadding),
                onFavoriteClick = onFavoriteClick,
                onDetailsClick = {}
            )
        }
    }
}
