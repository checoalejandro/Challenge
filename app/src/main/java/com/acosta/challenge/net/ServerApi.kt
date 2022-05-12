package com.acosta.challenge.net

import com.acosta.challenge.models.IPCHistoryResponse
import com.acosta.challenge.models.TopTenResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * GET Server responses.
 */
interface ServerApi {

    /**
     * Returns IPC history
     *
     * @return [IPCHistoryResponse]
     */
    @GET("/v3/cc4c350b-1f11-42a0-a1aa-f8593eafeb1e")
    suspend fun getIPC(): Response<IPCHistoryResponse>

    /**
     * Returns top ten indices.
     *
     * @return
     */
    @GET("/v3/b4eb963c-4aee-4b60-a378-20cb5b00678f")
    suspend fun getTopTen(): Response<TopTenResponse>

}