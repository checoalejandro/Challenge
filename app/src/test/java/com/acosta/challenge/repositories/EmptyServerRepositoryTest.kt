package com.acosta.challenge.repositories

import com.acosta.challenge.ChallengeTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Test
import org.koin.core.component.get
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class EmptyServerRepositoryTest : ChallengeTest() {

    private val repository: ServerRepositoryImpl by lazy { get<ServerRepositoryImpl>() }

    /**
     * Mocks responses
     */
    override fun setMockedServer(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().apply {
                    when (request.path) {
                        "/v3/cc4c350b-1f11-42a0-a1aa-f8593eafeb1e", "/v3/b4eb963c-4aee-4b60-a378-20cb5b00678f" -> {
                            setResponseCode(200)
                            setBody("[]")
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
            assert(response.isEmpty())
        }
    }

    /**
     * Expect always 2 elements without nulls.
     */
    @Test
    fun `successful request indices`() {
        runBlocking {
            val response = repository.getIndices()
            assertNotNull(response)
            assert(response.isEmpty())
        }
    }

    @Test
    fun `hot flow indices`() {
        runBlocking {
            val indices = repository.indicesFlow.first()
            assert(indices.isEmpty())
        }
    }

}