package ru.ifedorov.thousandcourses.mapper

import ru.ifedorov.domain.model.Course
import ru.ifedorov.ui.component.CourseCardUiModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Course.toCourseCardUiModel(): CourseCardUiModel {
    return CourseCardUiModel(
        id = id,
        title = title,
        description = description,
        price = "$price ₽",
        rating = rating,
        date = startDate.toCourseDateLabel(),
        isFavorite = isFavorite
    )
}

private fun String.toCourseDateLabel(): String {
    return runCatching {
        val date = LocalDate.parse(this)
        val month = date.format(CourseMonthFormatter).replaceFirstChar { char ->
            char.uppercase(RussianLocale)
        }

        "${date.dayOfMonth} $month ${date.year}"
    }.getOrElse {
        this
    }
}

private val RussianLocale = Locale.forLanguageTag("ru")
private val CourseMonthFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("MMMM", RussianLocale)
