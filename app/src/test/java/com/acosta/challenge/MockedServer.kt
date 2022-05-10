package com.acosta.challenge

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest

fun getMockedServer(): MockWebServer {
    val server = MockWebServer()
    server.dispatcher = dispatcher
    return server
}

private val dispatcher: Dispatcher = object :Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        MockResponse().apply {
            return when (request.path) {
                "/v3/cc4c350b-1f11-42a0-a1aa-f8593eafeb1e" -> {
                    setResponseCode(200)
                }
                else -> {
                    // Fallback to 404
                    setResponseCode(404)
                }
            }
        }
    }

}