import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart

class Chart(
    val xData: DoubleArray,
    val yData: DoubleArray,
    val chartName: String = "Chart",
    val xName: String = "x",
    val yName: String = "y",
    val lineName: String = "1 line"
) {
    fun draw() {
        val chart: XYChart = QuickChart.getChart(chartName, xName, yName, lineName, xData, yData);

        SwingWrapper(chart).displayChart()
    }
}