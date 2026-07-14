package ru.ifedorov.thousandcourses.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.ifedorov.courses.CoursesScreen
import ru.ifedorov.favorites.FavoritesScreen
import ru.ifedorov.thousandcourses.R
import ru.ifedorov.ui.component.CourseCardUiModel

@Composable
fun NavHost(
    navController: NavHostController,
    courses: List<CourseCardUiModel>,
    innerPadding: PaddingValues,
    onFavoriteClick: (courseId: Int) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = TopLevelDestination.Home.route
    ) {
        composable(route = TopLevelDestination.Home.route) {
            CoursesScreen(
                courses = courses,
                modifier = Modifier.padding(innerPadding),
                onFavoriteClick = onFavoriteClick,
                onDetailsClick = {},
                onFilterClick = {},
                onSortClick = {}
            )
        }
        composable(route = TopLevelDestination.Favorites.route) {
            FavoritesScreen(
                courses = courses.filter { it.isFavorite },
                modifier = Modifier.padding(innerPadding),
                onFavoriteClick = onFavoriteClick
            )
        }
        composable(route = TopLevelDestination.Account.route) {
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
    }
}
