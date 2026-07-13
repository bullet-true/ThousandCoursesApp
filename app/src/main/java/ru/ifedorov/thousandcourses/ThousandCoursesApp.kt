package ru.ifedorov.thousandcourses

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ru.ifedorov.courses.CoursesScreen
import ru.ifedorov.data.repository.AssetCourseRepository
import ru.ifedorov.favorites.FavoritesScreen
import ru.ifedorov.thousandcourses.mapper.toCourseCardUiModel
import ru.ifedorov.thousandcourses.ui.navigation.AppTab
import ru.ifedorov.thousandcourses.ui.navigation.BottomBar
import ru.ifedorov.ui.component.CourseCardUiModel
import ru.ifedorov.ui.theme.ThousandCoursesTheme

@Composable
fun ThousandCoursesApp() {
    ThousandCoursesTheme {
        var selectedTab by rememberSaveable { mutableStateOf(AppTab.Home) }
        var courses by remember { mutableStateOf(emptyList<CourseCardUiModel>()) }
        val context = LocalContext.current
        val courseRepository = remember(context) {
            AssetCourseRepository(assetManager = context.assets)
        }

        LaunchedEffect(courseRepository) {
            courses = courseRepository.getCourses().map { course -> course.toCourseCardUiModel() }
        }

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
                AppTab.Home -> CoursesContent(courses = courses, innerPadding = innerPadding)
                AppTab.Favorites -> FavoritesContent(courses = courses, innerPadding = innerPadding)
                AppTab.Account -> CoursesContent(courses = courses, innerPadding = innerPadding)
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
