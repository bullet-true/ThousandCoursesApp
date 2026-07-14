package ru.ifedorov.thousandcourses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.ifedorov.thousandcourses.ui.CoursesUiState
import ru.ifedorov.thousandcourses.ui.navigation.BottomBar
import ru.ifedorov.thousandcourses.ui.navigation.NavHost
import ru.ifedorov.thousandcourses.ui.navigation.TopLevelDestination
import ru.ifedorov.ui.component.CourseCardUiModel
import ru.ifedorov.ui.theme.ThousandCoursesTheme

@Composable
fun ThousandCoursesApp(
    coursesUiState: CoursesUiState,
    onFavoriteClick: (courseId: Int) -> Unit
) {
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
            when {
                coursesUiState.isLoading -> LoadingContent(innerPadding = innerPadding)

                coursesUiState.errorMessage != null -> ErrorContent(innerPadding = innerPadding)

                else -> LoadedContent(
                    navController = navController,
                    courses = coursesUiState.courses,
                    innerPadding = innerPadding,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}

@Composable
private fun LoadedContent(
    navController: NavHostController,
    courses: List<CourseCardUiModel>,
    innerPadding: PaddingValues,
    onFavoriteClick: (courseId: Int) -> Unit
) {
    NavHost(
        navController = navController,
        courses = courses,
        innerPadding = innerPadding,
        onFavoriteClick = onFavoriteClick
    )
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

private fun NavHostController.navigateToTab(tab: TopLevelDestination) {
    navigate(tab.route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
    }
}
