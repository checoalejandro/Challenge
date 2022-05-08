package com.acosta.challenge.repositories

import android.util.Log
import com.acosta.challenge.models.IPCHistoryResponse
import com.acosta.challenge.net.ServerApi
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
}

@Single
class ServerRepositoryImpl : ServerRepository, KoinComponent {

    private val TAG = this::class.java.simpleName

    override val service: ServerApi by inject()

    override suspend fun getIPC(): IPCHistoryResponse? {
        return try {
            service.getIPC().body()
        } catch (e: Exception) {
            Log.e(TAG, "getIPC: An error occurred when fetching IPC", e)
            null
        }
    }
}
