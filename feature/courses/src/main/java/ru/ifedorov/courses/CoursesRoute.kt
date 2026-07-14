package ru.ifedorov.courses

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.ifedorov.ui.component.CourseCardUiModel

@Composable
fun CoursesRoute(
    courses: List<CourseCardUiModel>,
    innerPadding: PaddingValues,
    onFavoriteClick: (courseId: Int) -> Unit,
    onDetailsClick: (courseId: Int) -> Unit,
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit
) {
    CoursesScreen(
        courses = courses,
        modifier = Modifier.padding(innerPadding),
        onFavoriteClick = onFavoriteClick,
        onDetailsClick = onDetailsClick,
        onFilterClick = onFilterClick,
        onSortClick = onSortClick
    )
}
