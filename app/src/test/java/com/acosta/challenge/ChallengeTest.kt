package com.acosta.challenge

import com.acosta.challenge.di.AppModule
import com.acosta.challenge.di.NetworkModule
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.ksp.generated.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.shadows.ShadowLog
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
abstract class ChallengeTest : KoinComponent {

    private lateinit var mocked: MockWebServer

    /**
     * For mocking responses, define responses
     */
    abstract fun setMockedServer(): Dispatcher

    @Before
    fun setup() {
        // Setup logger
        ShadowLog.stream = System.out

        // Setup mock server
        mocked = MockWebServer()
        mocked.dispatcher = setMockedServer()

        // Client for local testing.
        val client = OkHttpClient.Builder()
            .connectTimeout(500, TimeUnit.MILLISECONDS)
            .readTimeout(500, TimeUnit.MILLISECONDS)
            .writeTimeout(500, TimeUnit.MILLISECONDS)
            .pingInterval(500, TimeUnit.MILLISECONDS)
            .build()

        // Init koin
        startKoin {
            androidLogger()
            androidContext(RuntimeEnvironment.getApplication())
            modules(NetworkModule("http://127.0.0.1:8080", client).module)
            modules(AppModule().module)
        }

        mocked.start(8080)
        mocked.url("/")
    }

    /**
     * Reads json resource from local path.
     *
     * @param path
     * @return string
     */
    protected fun getLocalResponse(path: String) =
        javaClass.getResourceAsStream(path)?.reader()?.readText()
            ?: throw Exception("Resource '$path' not found")

    @After
    fun teardown() {
        stopKoin()
        mocked.shutdown()
    }
}