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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.ifedorov.courses.CoursesScreen
import ru.ifedorov.favorites.FavoritesScreen
import ru.ifedorov.thousandcourses.ui.CoursesUiState
import ru.ifedorov.thousandcourses.ui.navigation.AppTab
import ru.ifedorov.thousandcourses.ui.navigation.BottomBar
import ru.ifedorov.ui.component.CourseCardUiModel
import ru.ifedorov.ui.theme.ThousandCoursesTheme

@Composable
fun ThousandCoursesApp(coursesUiState: CoursesUiState) {
    ThousandCoursesTheme {
        var selectedTab by rememberSaveable { mutableStateOf(AppTab.Home) }

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                BottomBar(
                    selectedTab = selectedTab,
                    onTabClick = { tab -> selectedTab = tab }
                )
            }
        ) { innerPadding ->
            when {
                coursesUiState.isLoading -> LoadingContent(innerPadding = innerPadding)
                coursesUiState.errorMessage != null -> ErrorContent(innerPadding = innerPadding)
                else -> LoadedContent(
                    selectedTab = selectedTab,
                    courses = coursesUiState.courses,
                    innerPadding = innerPadding
                )
            }
        }
    }
}

@Composable
private fun LoadedContent(
    selectedTab: AppTab,
    courses: List<CourseCardUiModel>,
    innerPadding: PaddingValues
) {
    when (selectedTab) {
        AppTab.Home -> CoursesContent(courses = courses, innerPadding = innerPadding)
        AppTab.Favorites -> FavoritesContent(courses = courses, innerPadding = innerPadding)
        AppTab.Account -> CoursesContent(courses = courses, innerPadding = innerPadding)
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
private fun CoursesContent(
    courses: List<CourseCardUiModel>,
    innerPadding: PaddingValues
) {
    CoursesScreen(
        courses = courses,
        modifier = Modifier.padding(innerPadding),
        onFavoriteClick = {},
        onDetailsClick = {},
        onFilterClick = {},
        onSortClick = {}
    )
}

@Composable
private fun FavoritesContent(
    courses: List<CourseCardUiModel>,
    innerPadding: PaddingValues
) {
    FavoritesScreen(
        courses = courses.filter(CourseCardUiModel::isFavorite),
        modifier = Modifier.padding(innerPadding)
    )
}
