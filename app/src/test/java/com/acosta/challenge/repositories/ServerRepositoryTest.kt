package com.acosta.challenge.repositories

import com.acosta.challenge.ChallengeTest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Test
import org.koin.core.component.get
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ServerRepositoryTest : ChallengeTest() {

    private val repository: ServerRepository by lazy { get<ServerRepository>() }

    /**
     * Mocks responses
     */
    override fun setMockedServer(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().apply {
                    when (request.path) {
                        "/v3/cc4c350b-1f11-42a0-a1aa-f8593eafeb1e" -> {
                            setResponseCode(200)
                            setBody(getLocalResponse("/responses/successful_ipc.json"))
                        }
                        else -> setResponseCode(404)
                    }
                }
            }

        }
    }

    /**
     * Expect always 3 elements without nulls.
     */
    @Test
    fun `successful request ipc`() {
        runBlocking {
            val response = repository.getIPC()
            assertNotNull(response)
            assertEquals(3, response.count())
            assert(response.all { it.date.isNotEmpty() })

            val parsed = response.first().getFormattedDate()
            assertEquals("Aug 18, 2020, 12:01:43 AM", parsed)
        }
    }

}