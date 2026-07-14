package ru.ifedorov.ui.component

data class CourseCardUiModel(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val rating: String,
    val date: String,
    val isFavorite: Boolean,
    val imageUrl: String? = null
)
