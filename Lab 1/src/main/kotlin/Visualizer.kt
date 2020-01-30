
import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper

import org.knowm.xchart.XYChart


class Visualizer {
    companion object {
        lateinit var chart: XYChart

        @JvmStatic
        fun draw(dots: List<Dot>, dot: Dot) {
            val x = mutableListOf<Double>()
            val y = mutableListOf<Double>()

            for (singleDot in dots) {
                x.add(singleDot.x)
                y.add(singleDot.y)
            }

            x.add(dots[0].x)
            y.add(dots[0].y)

            chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", x, y)
            chart.addSeries("DOT", doubleArrayOf(dot.x), doubleArrayOf(dot.y))

            SwingWrapper(chart).displayChart()
        }

        @JvmStatic
        fun setTitle(string: String) {
            chart.title = string
        }
    }
}