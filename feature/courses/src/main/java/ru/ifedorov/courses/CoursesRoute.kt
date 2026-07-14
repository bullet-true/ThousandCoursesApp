package ru.ifedorov.courses

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.ifedorov.ui.component.CourseCardUiModel

@Composable
fun CoursesRoute(
    courses: List<CourseCardUiModel>,
    modifier: Modifier = Modifier,
    onFavoriteClick: (courseId: Int) -> Unit,
    onDetailsClick: (courseId: Int) -> Unit,
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit
) {
    CoursesScreen(
        courses = courses,
        modifier = modifier,
        onFavoriteClick = onFavoriteClick,
        onDetailsClick = onDetailsClick,
        onFilterClick = onFilterClick,
        onSortClick = onSortClick
    )
}
