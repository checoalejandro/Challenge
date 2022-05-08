package com.acosta.challenge.models

typealias TopTenResponse = ArrayList<IndexItem>

data class IndexItem (
    val issueID: String,
    val openPrice: Double,
    val maxPrice: Double,
    val minPrice: Double,
    val percentageChange: Double,
    val valueChange: Double,
    val aggregatedVolume: Long,
    val bidPrice: Double,
    val bidVolume: Long,
    val askPrice: Double,
    val askVolume: Long,
    val ipcParticipationRate: Double,
    val lastPrice: Double,
    val closePrice: Double,
    val riseLowTypeID: Long,
    val instrumentTypeID: Long,
    val benchmarkID: Long,
    val benchmarkPercentage: Long
)
