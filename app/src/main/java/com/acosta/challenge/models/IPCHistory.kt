package com.acosta.challenge.models

typealias IPCHistoryResponse = ArrayList<IPCHistory>

data class IPCHistory (
    val date: String,
    val price: Double,
    val percentageChange: Double,
    val volume: Long,
    val change: Double
)
