package ru.ifedorov.ui.mapper

import ru.ifedorov.domain.model.Course
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val ANDROID_ASSET_IMAGE_PATH = "file:///android_asset/images"
private const val COURSE_JAVA_ID = 100
private const val COURSE_3D_GENERALIST_ID = 101
private const val COURSE_PYTHON_ADVANCED_ID = 102

fun Course.toCourseCardUiModel(): ru.ifedorov.ui.component.CourseCardUiModel {
    return _root_ide_package_.ru.ifedorov.ui.component.CourseCardUiModel(
        id = id,
        title = title,
        description = description,
        price = "$price ₽",
        rating = rating,
        date = startDate.toCourseDateLabel(),
        isFavorite = isFavorite,
        imageUrl = toCourseImageUrl()
    )
}

private fun Course.toCourseImageUrl(): String? {
    val imageName = when (id) {
        COURSE_JAVA_ID -> "course_java.png"
        COURSE_3D_GENERALIST_ID -> "course_3d_generalist.png"
        COURSE_PYTHON_ADVANCED_ID -> "course_python_advanced.png"
        else -> return null
    }
    return "$ANDROID_ASSET_IMAGE_PATH/$imageName"
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
