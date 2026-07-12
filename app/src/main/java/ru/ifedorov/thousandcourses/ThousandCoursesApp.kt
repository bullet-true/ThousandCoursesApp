package ru.ifedorov.thousandcourses

import androidx.compose.runtime.Composable
import ru.ifedorov.courses.CoursesScreen
import ru.ifedorov.ui.component.CourseCardUiModel
import ru.ifedorov.ui.theme.ThousandCoursesTheme

@Composable
fun ThousandCoursesApp() {
    ThousandCoursesTheme {
        CoursesScreen(
            courses = sample,
            onFavoriteClick = {},
            onDetailsClick = {},
            onFilterClick = {},
            onSortClick = {}
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
