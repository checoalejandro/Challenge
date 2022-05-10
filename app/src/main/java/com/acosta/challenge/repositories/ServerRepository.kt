package com.acosta.challenge.repositories

import android.util.Log
import com.acosta.challenge.models.IPCHistoryResponse
import com.acosta.challenge.models.TopTenResponse
import com.acosta.challenge.net.ServerApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Server Repository
 *
 * @author @seacosta
 */
interface ServerRepository {

    val service: ServerApi

    /**
     * Fetch IPC history from server.
     *
     * @return [IPCHistoryResponse]
     */
    suspend fun getIPC(): IPCHistoryResponse?

    /**
     * Fetches top ten list.
     *
     * @return [TopTenResponse]
     */
    suspend fun getIndices(): TopTenResponse?
}

@Single
class ServerRepositoryImpl : ServerRepository, KoinComponent {

    private val TAG = this::class.java.simpleName
    private val REFRESH_INTERVAL = 5000L

    override val service: ServerApi by inject()

    override suspend fun getIPC(): IPCHistoryResponse? {
        return try {
            service.getIPC().body()
        } catch (e: Exception) {
            Log.e(TAG, "getIPC: An error occurred when fetching IPC", e)
            null
        }
    }

    override suspend fun getIndices(): TopTenResponse? {
        return try {
            service.getTopTen().body()
        } catch (e: Exception) {
            Log.e(TAG, "getIPC: An error occurred when fetching top indices", e)
            null
        }
    }

    /**
     * Constant refreshing hot flow of indices content.
     */
    val indicesFlow: Flow<TopTenResponse> = flow {
        while (true) {
            getIndices()?.let {
                emit(it)
                delay(REFRESH_INTERVAL)
            }
        }
    }
}
