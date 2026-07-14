package ru.ifedorov.favorites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.ifedorov.ui.component.CourseCardUiModel

@Composable
fun FavoritesRoute(
    courses: List<CourseCardUiModel>,
    innerPadding: PaddingValues,
    onFavoriteClick: (courseId: Int) -> Unit,
    onDetailsClick: (courseId: Int) -> Unit
) {
    FavoritesScreen(
        courses = courses.filter { it.isFavorite },
        modifier = Modifier.padding(innerPadding),
        onFavoriteClick = onFavoriteClick,
        onDetailsClick = onDetailsClick
    )
}
