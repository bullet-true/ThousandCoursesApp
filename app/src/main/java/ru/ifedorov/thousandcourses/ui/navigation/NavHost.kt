package ru.ifedorov.thousandcourses.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.ifedorov.courses.CoursesScreen
import ru.ifedorov.favorites.FavoritesScreen
import ru.ifedorov.thousandcourses.R
import ru.ifedorov.thousandcourses.ui.CoursesUiState
import ru.ifedorov.thousandcourses.ui.CoursesViewModel
import ru.ifedorov.ui.component.CourseCardUiModel

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

                CoursesRoute(
                    uiState = uiState,
                    innerPadding = innerPadding,
                    onFavoriteClick = viewModel::onFavoriteClick
                )
            }
            composable(route = TopLevelDestination.Favorites.route) {
                val viewModel = sharedCoursesViewModel(navController)
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                FavoritesRoute(
                    uiState = uiState,
                    innerPadding = innerPadding,
                    onFavoriteClick = viewModel::onFavoriteClick
                )
            }
            composable(route = TopLevelDestination.Account.route) {
                AccountPlaceholder(innerPadding = innerPadding)
            }
        }
    }
}

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
private fun CoursesRoute(
    uiState: CoursesUiState,
    innerPadding: PaddingValues,
    onFavoriteClick: (courseId: Int) -> Unit
) {
    CourseStateContent(
        uiState = uiState,
        innerPadding = innerPadding,
        onContent = { courses ->
            CoursesScreen(
                courses = courses,
                modifier = Modifier.padding(innerPadding),
                onFavoriteClick = onFavoriteClick,
                onDetailsClick = {},
                onFilterClick = {},
                onSortClick = {}
            )
        }
    )
}

@Composable
private fun FavoritesRoute(
    uiState: CoursesUiState,
    innerPadding: PaddingValues,
    onFavoriteClick: (courseId: Int) -> Unit
) {
    CourseStateContent(
        uiState = uiState,
        innerPadding = innerPadding,
        onContent = { courses ->
            FavoritesScreen(
                courses = courses.filter(CourseCardUiModel::isFavorite),
                modifier = Modifier.padding(innerPadding),
                onFavoriteClick = onFavoriteClick
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

        uiState.errorMessage != null -> ErrorContent(innerPadding = innerPadding)

        else -> onContent(uiState.courses)
    }
}

@Composable
private fun LoadingContent(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun ErrorContent(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.courses_loading_error),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun AccountPlaceholder(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_tab_account),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
