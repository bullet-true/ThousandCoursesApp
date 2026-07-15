package ru.ifedorov.network.datasource

import ru.ifedorov.network.model.NetworkCourseDto

interface CoursesNetworkDataSource {

    suspend fun getCourses(): List<NetworkCourseDto>
}
