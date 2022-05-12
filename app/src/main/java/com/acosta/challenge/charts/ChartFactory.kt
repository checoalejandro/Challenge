package com.acosta.challenge.charts

import com.acosta.challenge.R
import com.acosta.challenge.charts.parser.ChallengeParser
import com.acosta.challenge.models.IPCHistoryResponse
import com.github.mikephil.charting.charts.Chart

enum class ChartFactory {
    LINE;

    /**
     * If one of these chart types requires different parsing,
     * This can be defined.
     *
     * @param response [IPCHistoryResponse]
     * @param parser associate chart
     */
    open fun parse(response: IPCHistoryResponse, parser: ChallengeParser) {
        parser.setGlobalProperties()
        parser.setSecondaryProperties()
        val data = response.map { it.price }
        val legend = (parser.chart as Chart<*>).context.getString(R.string.main_ipc_legend)
        parser.build(mapOf(legend to data))
    }
}