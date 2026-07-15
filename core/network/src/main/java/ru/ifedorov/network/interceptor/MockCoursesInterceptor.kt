package ru.ifedorov.network.interceptor

import android.content.res.AssetManager
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

private const val COURSES_PATH = "/courses"
private const val COURSES_ASSET_NAME = "courses.json"
private const val JSON_MEDIA_TYPE = "application/json"

class MockCoursesInterceptor @Inject constructor(
    private val assetManager: AssetManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (request.url.encodedPath != COURSES_PATH) {
            return chain.proceed(request)
        }

        val responseBody = assetManager.open(COURSES_ASSET_NAME).bufferedReader().use { reader ->
            reader.readText()
        }.toResponseBody(JSON_MEDIA_TYPE.toMediaType())

        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(HTTP_OK)
            .message("OK")
            .body(responseBody)
            .build()
    }

    private companion object {
        const val HTTP_OK = 200
    }
}
