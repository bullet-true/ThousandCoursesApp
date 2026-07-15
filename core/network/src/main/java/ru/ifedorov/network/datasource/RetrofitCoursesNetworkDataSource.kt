package ru.ifedorov.network.datasource

import ru.ifedorov.network.api.CoursesApi
import ru.ifedorov.network.model.NetworkCourseDto
import javax.inject.Inject

class RetrofitCoursesNetworkDataSource @Inject constructor(
    private val coursesApi: CoursesApi
) : CoursesNetworkDataSource {

    override suspend fun getCourses(): List<NetworkCourseDto> =
        coursesApi.getCourses().courses
}
