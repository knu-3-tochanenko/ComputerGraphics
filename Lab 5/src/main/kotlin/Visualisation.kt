import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.style.markers.SeriesMarkers
import java.awt.Color
import kotlin.math.atan2

class Visualisation {
    private val chart: XYChart = XYChartBuilder()
        .width(600)
        .height(400)
        .xAxisTitle("X")
        .yAxisTitle("Y").build()
    private val swingWrapper = SwingWrapper(chart)

    fun drawPoints(dots: List<Dot>) {

        dots.forEach {
            chart.addSeries(
                "Point $it",
                doubleArrayOf(it.x),
                doubleArrayOf(it.y)
            ).setMarkerColor(Color.BLACK)
                .setMarker(SeriesMarkers.CIRCLE)
                .setLineColor(Color.BLACK)
        }

        swingWrapper.displayChart()
        swingWrapper.repaintChart()
    }

    fun drawHull(dots: MutableList<Dot>) {
        val center = Dot(0.0, 0.0)
        dots.forEach {
            center.y += it.y / dots.size
        }

        dots.map { it.angle = atan2(it.y - center.y, it.x - center.x) * 180 / Math.PI }

        dots.sortBy { it.angle }

        val x = mutableListOf<Double>()
        val y = mutableListOf<Double>()

        dots.forEach {
            x.add(it.x)
            y.add(it.y)
        }
        x.add(dots[0].x)
        y.add(dots[0].y)

        chart.addSeries(
            "Hull $dots", x, y
        ).setMarkerColor(Color.BLACK)
            .setMarker(SeriesMarkers.CIRCLE)
            .setLineColor(Color.BLACK)

        swingWrapper.repaintChart()

    }
}