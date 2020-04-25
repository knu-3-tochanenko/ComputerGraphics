import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart

class Chart(
    private val chartName: String = "Chart",
    private val xName: String = "x",
    private val yName: String = "y"
) {

    private var lines = mutableListOf<Line>()

    fun add(line: Line) {
        lines.add(line)
    }

    fun draw() {
        val chart: XYChart =
            QuickChart.getChart(chartName, xName, yName, "0, 0", doubleArrayOf(0.0), doubleArrayOf(0.0))

        for (line in lines)
            chart.addSeries(line.lineName, line.xData, line.yData)

        SwingWrapper(chart).displayChart()
    }
}