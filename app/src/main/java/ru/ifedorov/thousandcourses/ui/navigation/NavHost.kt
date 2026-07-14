package ru.ifedorov.thousandcourses.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import ru.ifedorov.thousandcourses.R
import ru.ifedorov.thousandcourses.ui.CoursesUiState
import ru.ifedorov.thousandcourses.ui.CoursesViewModel
import ru.ifedorov.ui.component.CourseCardUiModel
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
                val viewModel = sharedCoursesViewModel(navController)
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                CoursesStateRoute(
                    uiState = uiState,
                    innerPadding = innerPadding,
                    onFavoriteClick = { courseId ->
                        viewModel.onFavoriteClick(courseId)
                    }
                )
            }
            composable(route = TopLevelDestination.Favorites.route) {
                val viewModel = sharedCoursesViewModel(navController)
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
                AccountRoute(innerPadding = innerPadding)
            }
        }
    }
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
private fun sharedCoursesViewModel(
    navController: NavHostController
): CoursesViewModel {
    val parentEntry = remember(navController) {
        navController.getBackStackEntry(MAIN_GRAPH_ROUTE)
    }
    return hiltViewModel(parentEntry)
}

@Composable
private fun CoursesStateRoute(
    uiState: CoursesUiState,
    innerPadding: PaddingValues,
    onFavoriteClick: (courseId: Int) -> Unit
) {
    CourseStateContent(
        uiState = uiState,
        innerPadding = innerPadding,
        onContent = { courses ->
            CoursesRoute(
                courses = courses,
                innerPadding = innerPadding,
                onFavoriteClick = onFavoriteClick,
                onDetailsClick = {},
                onFilterClick = {},
                onSortClick = {}
            )
        }
    )
}

@Composable
private fun FavoritesStateRoute(
    uiState: CoursesUiState,
    innerPadding: PaddingValues,
    onFavoriteClick: (courseId: Int) -> Unit
) {
    CourseStateContent(
        uiState = uiState,
        innerPadding = innerPadding,
        onContent = { courses ->
            FavoritesRoute(
                courses = courses,
                innerPadding = innerPadding,
                onFavoriteClick = onFavoriteClick,
                onDetailsClick = {}
            )
        }
    )
}

@Composable
private fun CourseStateContent(
    uiState: CoursesUiState,
    innerPadding: PaddingValues,
    onContent: @Composable (courses: List<CourseCardUiModel>) -> Unit
) {
    when {
        uiState.isLoading -> LoadingContent(innerPadding = innerPadding)

        uiState.errorMessage != null -> ErrorContent(
            innerPadding = innerPadding,
            message = stringResource(id = R.string.courses_loading_error)
        )

        else -> onContent(uiState.courses)
    }
}
