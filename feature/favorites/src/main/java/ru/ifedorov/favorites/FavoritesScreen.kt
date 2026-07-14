package ru.ifedorov.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ifedorov.ui.component.CourseCard
import ru.ifedorov.ui.component.CourseCardUiModel
import ru.ifedorov.ui.theme.ThousandCoursesTheme

private val ScreenHorizontalPadding = 16.dp
private val ScreenTopPadding = 16.dp
private val FavoriteCardSpacing = 16.dp
private val FavoritesListBottomPadding = 24.dp

@Composable
fun FavoritesScreen(
    courses: List<CourseCardUiModel>,
    modifier: Modifier = Modifier,
    onFavoriteClick: (courseId: Int) -> Unit,
    onDetailsClick: (courseId: Int) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                start = ScreenHorizontalPadding,
                end = ScreenHorizontalPadding,
                top = ScreenTopPadding
            )
    ) {
        Text(
            text = stringResource(id = R.string.favorites_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (courses.isEmpty()) {
            EmptyFavoritesContent()
        } else {
            FavoriteCoursesList(
                courses = courses,
                onFavoriteClick = onFavoriteClick,
                onDetailsClick = onDetailsClick
            )
        }
    }
}

@Composable
private fun FavoriteCoursesList(
    courses: List<CourseCardUiModel>,
    onFavoriteClick: (courseId: Int) -> Unit,
    onDetailsClick: (courseId: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(FavoriteCardSpacing),
        contentPadding = PaddingValues(bottom = FavoritesListBottomPadding)
    ) {
        items(
            items = courses,
            key = CourseCardUiModel::id
        ) { course ->
            CourseCard(
                course = course,
                onFavoriteClick = onFavoriteClick,
                onDetailsClick = onDetailsClick
            )
        }
    }
}

@Composable
private fun EmptyFavoritesContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.favorites_empty_message),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun FavoritesScreenPreview() {
    ThousandCoursesTheme {
        FavoritesScreen(
            courses = sample,
            onFavoriteClick = {},
        )
    }
}

@Preview
@Composable
private fun EmptyFavoritesScreenPreview() {
    ThousandCoursesTheme {
        FavoritesScreen(
            courses = emptyList(),
            onFavoriteClick = {},
        )
    }
}

private val sample = listOf(
    CourseCardUiModel(
        id = 1,
        title = "Java-разработчик с нуля",
        description = "Освойте backend-разработку и программирование на Java, фреймворки " +
            "Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, " +
            "собрав портфолио и став востребованным специалистом для любой IT компании.",
        price = "999 ₽",
        rating = "4.9",
        date = "22 Мая 2024",
        isFavorite = true
    ),
    CourseCardUiModel(
        id = 2,
        title = "3D-дженералист",
        description = "Освой профессию 3D-дженералиста и стань универсальным специалистом, " +
            "который умеет создавать 3D-модели, текстуры и анимации, а также может строить " +
            "карьеру в геймдеве, кино, рекламе или дизайне.",
        price = "12 000 ₽",
        rating = "3.9",
        date = "10 Сентября 2024",
        isFavorite = true
    ),
)
