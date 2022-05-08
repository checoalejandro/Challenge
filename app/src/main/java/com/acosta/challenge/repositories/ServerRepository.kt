package com.acosta.challenge.repositories

import org.koin.core.annotation.Single

interface ServerRepository {

    fun getIPC(): String
}

@Single
class ServerRepositoryImpl : ServerRepository{
    override fun getIPC(): String {
        return "Not yet"
    }
}
