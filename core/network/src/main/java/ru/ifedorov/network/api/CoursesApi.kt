package ru.ifedorov.network.api

import retrofit2.http.GET
import ru.ifedorov.network.model.NetworkCoursesResponse

interface CoursesApi {

    @GET("courses")
    suspend fun getCourses(): NetworkCoursesResponse
}
