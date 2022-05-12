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
    val riseLowTypeId: Int,
    val instrumentTypeId: Long,
    val benchmarkId: Long,
    val benchmarkPercentage: Long
) {
    /**
     * Returns [RaiseType] enum value.
     * Default value is [RaiseType.DEFAULT]
     */
    fun getRaiseType() = RaiseType.getRaiseType(riseLowTypeId)

}

enum class RaiseType(val id: Int) {
    RAISE(2),
    LOW(1),
    VOLUME(3),
    DEFAULT(4);

    companion object {
        fun getRaiseType(id: Int) = values().firstOrNull { it.id == id } ?: DEFAULT
    }
}


