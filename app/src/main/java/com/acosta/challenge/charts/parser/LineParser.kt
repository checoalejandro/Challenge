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

    val chart: ChartInterface

    fun setGlobalProperties()

    fun setSecondaryProperties()

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
        chart.description.isEnabled = false
        chart.xAxis.isEnabled = false
        chart.data = LineData(dataSets)
        chart.invalidate()
    }


}