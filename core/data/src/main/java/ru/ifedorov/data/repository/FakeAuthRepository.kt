package ru.ifedorov.data.repository

import kotlinx.coroutines.delay
import ru.ifedorov.domain.repository.AuthRepository
import kotlin.time.Duration.Companion.milliseconds

class FakeAuthRepository : AuthRepository {

    override suspend fun login(email: String, password: String): Boolean {
        delay(DELAY_MS.milliseconds)

        return email == TEST_EMAIL && password == TEST_PASSWORD
    }

    private companion object {
        private const val DELAY_MS = 500L
        private const val TEST_EMAIL = "test@test.com"
        private const val TEST_PASSWORD = "123"
    }
}
