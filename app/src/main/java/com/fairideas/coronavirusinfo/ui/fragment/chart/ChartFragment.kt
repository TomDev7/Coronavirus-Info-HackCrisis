package com.fairideas.coronavirusinfo.ui.fragment.chart

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.DisplayMetrics
import android.view.View
import androidx.lifecycle.Observer
import com.fairideas.coronavirusinfo.R
import com.fairideas.coronavirusinfo.data.DataStorage
import com.fairideas.coronavirusinfo.data.model.ContaminationArea
import com.fairideas.coronavirusinfo.ui.common.fragment.BaseFragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.chart_fragment.*

/**
 * Created by Illia Herman on 21.03.2020.
 */
class ChartFragment : BaseFragment(R.layout.chart_fragment) {
    override fun getToolbarTitle(): String = getString(R.string.bottom_nav_stats)

    val MATERIAL_COLORS_CUSTOM = intArrayOf(
        ColorTemplate.rgb("#2ecc71"),
        ColorTemplate.rgb("#f1c40f"),
        ColorTemplate.rgb("#e74c3c"),
        ColorTemplate.rgb("#3498db"),
        ColorTemplate.rgb("#9C27B0")
    )

    private var totalCases = 0
    private var totalRecovered = 0
    private var totalDeaths = 0
    private var allData = ArrayList<ContaminationArea>()
    private val valuesCases = ArrayList<Entry>()
    private val valuesRecovered = ArrayList<Entry>()
    private val valuesDeaths = ArrayList<Entry>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData() {
        DataStorage.areasLiveData.observe(viewLifecycleOwner, Observer {
            if (it) {
                prepareData()
                initView()
            }
        })

    }

    private fun prepareData() {
        var count = 0
        DataStorage.contaminationAreas?.forEach {
            count++
            totalCases += it.num_of_infected
            totalRecovered += it.num_of_recoveries
            totalDeaths += it.num_of_deaths
            valuesCases.add(
                Entry(
                    count.toFloat(),
                    totalCases.toFloat()
                )
            )
            valuesRecovered.add(
                Entry(
                    count.toFloat(),
                    totalRecovered.toFloat()
                )
            )
            valuesDeaths.add(
                Entry(
                    count.toFloat(),
                    totalDeaths.toFloat()
                )
            )
            allData.add(it)
        }

        allData.sortBy { it.num_of_infected }.apply { allData.reverse() }
    }

    private fun initView() {
        initLineChart()
        initPieChart()
    }

    private fun initPieChart() {

        chartPie.apply {
            setBackgroundColor(Color.WHITE)
            setUsePercentValues(true)
            chartPie.description.isEnabled = false

            centerText = generateCenterSpannableText()

            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)

            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)

            holeRadius = 64f
            transparentCircleRadius = 61f

            setDrawCenterText(true)

            isRotationEnabled = false
            isHighlightPerTapEnabled = true

            maxAngle = 180f // HALF chartPie

            rotationAngle = 360f
            setCenterTextOffset(0f, 30f)

            setData()

            setUsePercentValues(false)

            animateY(1400, Easing.EaseInOutQuad)
        }

        val displayMetrics = DisplayMetrics()
        getMainActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val offset = (height * 0.08).toFloat() /* percent to move */
        chartPie.setExtraOffsets(0f, -offset, 0f, 0f)

        val legend: Legend = chartPie.legend

        legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            orientation = Legend.LegendOrientation.HORIZONTAL
            isWordWrapEnabled = true
            xEntrySpace = 10f
            yEntrySpace = 0f
            yOffset = 10f
        }

        chartPie.setEntryLabelColor(Color.WHITE)
        chartPie.setEntryLabelTextSize(10f)
    }

    private fun setData(count: Int = 5) {
        val values = ArrayList<PieEntry>()
        for (i in 0 until count) {
            values.add(
                PieEntry(
                    allData.get(i).num_of_infected.toFloat(),
                    allData.get(i).country
                )
            )
        }
        val dataSet = PieDataSet(values, "")

        dataSet.apply {
            sliceSpace = 4f
            selectionShift = 6f
            setColors(*MATERIAL_COLORS_CUSTOM)
        }

        val data = PieData(dataSet)

        data.apply {
            setValueFormatter(PercentFormatter())
            setValueTextSize(12f)
            setValueTextColor(Color.WHITE)
        }
        chartPie.data = data
        chartPie.invalidate()
    }

    private fun generateCenterSpannableText(): SpannableString? {
        val s = SpannableString("The Countries With\nThe Most Reported Cases")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 18, 0)
        return s
    }

    private fun initLineChart() {
        lineChart.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            data = getComplexity()
            animateX(1400)
            axisRight.isEnabled = false
            xAxis.isEnabled = false
        }

        lineChartDesc.text =
            "Total Cases: $totalCases  |  Total Recovered: $totalRecovered  |  Total Deaths: $totalDeaths"
    }

    private fun getComplexity(): LineData? {
        val sets = ArrayList<ILineDataSet>()

        val ds1 = LineDataSet(valuesCases, "Cases")
        val ds2 = LineDataSet(valuesRecovered, "Recovered")
        val ds3 = LineDataSet(valuesDeaths, "Deaths")

        ds1.color = ColorTemplate.VORDIPLOM_COLORS[3]
        ds2.color = ColorTemplate.VORDIPLOM_COLORS[0]
        ds3.color = ColorTemplate.VORDIPLOM_COLORS[4]

        ds1.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[3])
        ds2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0])
        ds3.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[4])

        ds1.lineWidth = 2.5f
        ds1.circleRadius = 1f
        ds2.lineWidth = 2.5f
        ds2.circleRadius = 1f
        ds3.lineWidth = 2.5f
        ds3.circleRadius = 1f

        sets.add(ds1)
        sets.add(ds2)
        sets.add(ds3)

        return LineData(sets)
    }

    companion object {
        val TAG = ChartFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() = ChartFragment()
    }
}