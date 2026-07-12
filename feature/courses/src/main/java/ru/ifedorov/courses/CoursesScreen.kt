package ru.ifedorov.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ifedorov.ui.component.CourseCard
import ru.ifedorov.ui.component.CourseCardUiModel
import ru.ifedorov.ui.theme.ThousandCoursesTheme

private val ScreenHorizontalPadding = 16.dp
private val ScreenTopPadding = 16.dp
private val SearchToolbarHeight = 56.dp
private val SearchToolbarCornerRadius = 28.dp
private val SearchToolbarGap = 8.dp
private val SearchBarPadding = 16.dp
private val SearchBarContentGap = 16.dp
private val SearchIconSize = 24.dp
private val FilterIconSize = 24.dp
private val FilterButtonSize = 56.dp
private val FilterButtonIconPadding = 16.dp
private val SortIconSize = 16.dp
private val SortIconGap = 4.dp
private val CourseCardSpacing = 16.dp
private val CoursesListBottomPadding = 24.dp

@Composable
fun CoursesScreen(
    courses: List<CourseCardUiModel>,
    modifier: Modifier = Modifier,
    onFavoriteClick: (courseId: Int) -> Unit,
    onDetailsClick: (courseId: Int) -> Unit,
    onFilterClick: () -> Unit,
    onSortClick: () -> Unit
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
        SearchToolbar(onFilterClick = onFilterClick)

        Spacer(modifier = Modifier.height(16.dp))

        SortLabel(onClick = onSortClick)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(CourseCardSpacing),
            contentPadding = PaddingValues(bottom = CoursesListBottomPadding)
        ) {
            items(
                items = courses,
                key = { it.id }
            ) { course ->
                CourseCard(
                    course = course,
                    onFavoriteClick = onFavoriteClick,
                    onDetailsClick = onDetailsClick
                )
            }
        }
    }
}

@Composable
private fun SearchToolbar(
    onFilterClick: () -> Unit
) {
    val searchState = rememberTextFieldState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(SearchToolbarHeight),
        horizontalArrangement = Arrangement.spacedBy(SearchToolbarGap),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            state = searchState,
            modifier = Modifier.weight(1f)
        )
        FilterButton(onClick = onFilterClick)
    }
}

@Composable
private fun SearchBar(
    state: TextFieldState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(SearchToolbarHeight)
            .clip(RoundedCornerShape(SearchToolbarCornerRadius))
            .background(MaterialTheme.colorScheme.surface)
            .padding(SearchBarPadding),
        horizontalArrangement = Arrangement.spacedBy(SearchBarContentGap),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(SearchIconSize)
        )
        BasicTextField(
            state = state,
            modifier = Modifier.weight(1f),
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorator = { innerTextField ->
                if (state.text.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.courses_search_placeholder),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
private fun FilterButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(FilterButtonSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
            .clickable(role = Role.Button) { onClick() }
            .padding(FilterButtonIconPadding),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = stringResource(id = R.string.courses_filter_content_description),
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(FilterIconSize)
        )
    }
}

@Composable
private fun SortLabel(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button) { onClick() },
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.courses_sort_by_date),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.size(SortIconGap))

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_down_up),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(SortIconSize)
        )
    }
}

@Preview
@Composable
private fun CoursesScreenPreview() {
    ThousandCoursesTheme {
        CoursesScreen(
            courses = sampleCourses,
            onFavoriteClick = {},
            onDetailsClick = {},
            onFilterClick = {},
            onSortClick = {}
        )
    }
}

@Preview
@Composable
private fun SearchToolbarPreview() {
    ThousandCoursesTheme {
        SearchToolbar(
            onFilterClick = {}
        )
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    ThousandCoursesTheme {
        val searchState = rememberTextFieldState()

        SearchBar(
            state = searchState
        )
    }
}

@Preview
@Composable
private fun SortLabelPreview() {
    ThousandCoursesTheme {
        SortLabel(
            onClick = {}
        )
    }
}

private val sampleCourses = listOf(
    CourseCardUiModel(
        id = 1,
        title = "Java-разработчик с нуля",
        description = "Освойте backend-разработку и программирование на Java, фреймворки " +
            "Spring и Maven, работу с базами данных и API. Создайте свой собственный проект, " +
            "собрав портфолио и став востребованным специалистом для любой IT компании.",
        price = "999 ₽",
        rating = "4.9",
        date = "22 Мая 2024",
        isFavorite = false
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
    CourseCardUiModel(
        id = 3,
        title = "Python Advanced. Для продвинутых",
        description = "Вы узнаете, как разрабатывать гибкие и высокопроизводительные серверные " +
            "приложения на языке Kotlin. Преподаватели на вебинарах покажут пример того, " +
            "как разрабатывается проект маркетплейса: от идеи и постановки задачи – " +
            "до конечного решения",
        price = "1 299 ₽",
        rating = "4.3",
        date = "12 Октября 2024",
        isFavorite = false
    )
)
