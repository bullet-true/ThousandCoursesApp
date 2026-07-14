package ru.ifedorov.thousandcourses

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
            when (selectedTab) {
                AppTab.Home -> CoursesContent(
                    courses = coursesUiState.courses,
                    innerPadding = innerPadding
                )

                AppTab.Favorites -> FavoritesContent(
                    courses = coursesUiState.courses,
                    innerPadding = innerPadding
                )

                AppTab.Account -> CoursesContent(
                    courses = coursesUiState.courses,
                    innerPadding = innerPadding
                )
            }
        }
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
