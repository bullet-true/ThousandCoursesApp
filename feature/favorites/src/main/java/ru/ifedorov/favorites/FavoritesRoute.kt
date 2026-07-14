package ru.ifedorov.favorites

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.ifedorov.ui.component.CourseCardUiModel

@Composable
fun FavoritesRoute(
    courses: List<CourseCardUiModel>,
    modifier: Modifier = Modifier,
    onFavoriteClick: (courseId: Int) -> Unit,
    onDetailsClick: (courseId: Int) -> Unit
) {
    FavoritesScreen(
        courses = courses.filter { it.isFavorite },
        modifier = modifier,
        onFavoriteClick = onFavoriteClick,
        onDetailsClick = onDetailsClick
    )
}
