package com.acosta.challenge.models

import android.icu.text.DecimalFormat

typealias TopTenResponse = ArrayList<IndexItem>

data class IndexItem(
    val issueId: String,
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
    val riseLowTypeId: Long,
    val instrumentTypeId: Long,
    val benchmarkId: Long,
    val benchmarkPercentage: Long
)


