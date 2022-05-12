package com.acosta.challenge.charts.parser

import androidx.core.content.ContextCompat
import com.acosta.challenge.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface

/**
 * Parent parser.
 */
interface ChallengeParser {

    /**
     * Main chart object
     */
    val chart: ChartInterface

    /**
     * Properties that applies to all charts
     */
    fun setGlobalProperties()

    /**
     * Properties that applies to specific chart type
     */
    fun setSecondaryProperties()

    /**
     * Builds into data and sets to the chart
     *
     * @param values map key is label, list are y values.
     */
    fun build(values: Map<String, List<Double>>)

}

/**
 * Parses data into a line chart.
 *
 * @param chart [LineChart]
 */
class LineParser(
    override val chart: LineChart
) : ChallengeParser {

    override fun setGlobalProperties() {
        val textColor = chart.context.getColor(R.color.color_text_primary)
        chart.xAxis.textColor = textColor
        chart.axisLeft.textColor = textColor
        chart.axisRight.textColor = textColor
    }

    override fun setSecondaryProperties() {
        chart.description.isEnabled = false
        chart.xAxis.isEnabled = false
    }

    override fun build(values: Map<String, List<Double>>) {
        val dataSets = values.map { measure ->
            val entries =
                measure.value.mapIndexed { index, d -> Entry(index.toFloat(), d.toFloat()) }
            val dataSet = LineDataSet(entries, measure.key)
            dataSet.apply {
                axisDependency = YAxis.AxisDependency.LEFT
                setColor(ContextCompat.getColor(chart.context, R.color.color_text_red))
                setDrawCircles(false)
            }
        }.toList()
        chart.data = LineData(dataSets)
        chart.invalidate()
    }


}