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
import ru.ifedorov.thousandcourses.ui.navigation.AppTab
import ru.ifedorov.thousandcourses.ui.navigation.BottomBar
import ru.ifedorov.ui.component.CourseCardUiModel
import ru.ifedorov.ui.theme.ThousandCoursesTheme

@Composable
fun ThousandCoursesApp() {
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
            CoursesContent(innerPadding = innerPadding)
        }
    }
}

@Composable
private fun CoursesContent(
    innerPadding: PaddingValues
) {
    CoursesScreen(
        courses = sample,
        modifier = Modifier.padding(innerPadding),
        onFavoriteClick = {},
        onDetailsClick = {},
        onFilterClick = {},
        onSortClick = {}
    )
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
