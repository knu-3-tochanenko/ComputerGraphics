import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.text.Font
import java.net.URL
import java.util.*
import kotlin.math.sqrt


class AppController : Initializable {
    @FXML
    var canvas: Canvas? = null

    @FXML
    var nextButton: Button? = null
    private var context: GraphicsContext? = null
    private val diameter = 6.0
    private var firstRectanglePoint: Point? = null
    private val points: ArrayList<Point> = ArrayList<Point>()
    private var rectangle: Rectangle? = null
    private var rectangleInput = false
    private var rectangleInputEnded = false
    private var debug = false
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        context = canvas?.graphicsContext2D
        context!!.font = Font(null, 10.0)
        onReset()
    }

    private fun hullEdge(points: ArrayList<Point>, r: Int): Edge? {
        val l = 0
        if (l >= r) {
            return null
        }
        var minXPoint: Point = points[l]
        for (i in l until r) {
            val point: Point = points[i]
            if (point.x < minXPoint.x) minXPoint = point
        }
        val firstPoint = Point(minXPoint.x + 0.1, minXPoint.y)
        val secondPoint: Point = minXPoint
        val curVector = Point(secondPoint.x - firstPoint.x, secondPoint.y - firstPoint.y)
        val vectorLength: Double = sqrt(curVector.x * curVector.x + curVector.y * curVector.y)
        var nextPoint: Point? = null
        var maxCos = -2.0
        for (i in l until r) {
            val point: Point = points[i]
            if (point === firstPoint || point === secondPoint) continue
            val diffY: Double = secondPoint.y - point.y
            val diffX: Double = secondPoint.x - point.x
            val length: Double = sqrt(diffX * diffX + diffY * diffY)
            val curCos: Double = (-diffX * curVector.x + -diffY * curVector.y) / (vectorLength * length)
            if (curCos > maxCos) {
                nextPoint = point
                maxCos = curCos
            }
        }
        return nextPoint?.let { Edge(minXPoint, it) }
    }

    @FXML
    fun onReset() {
        context!!.fill = Color.BLACK
        rectangleInput = false
        canvas?.let { context!!.clearRect(0.0, 0.0, it.width, it.height) }
    }

    private fun deloneTriangulation(points: ArrayList<Point>): ArrayList<Edge?> {
        var startEdge = hullEdge(points, points.size)
        if (startEdge != null) {
            if (startEdge.start.y < startEdge.end.y) startEdge = Edge(startEdge.end, startEdge.start)
        }
        val result = ArrayList<Edge?>()
        val liveEdges = TreeSet(Comparator { a: Edge, b: Edge ->
            val comp: Int = a.start.compareTo(b.start)
            if (comp != 0) return@Comparator comp
            a.end.compareTo(b.end)
        })
        liveEdges.add(startEdge)
        startEdge?.let { NormalLine.bisector(it) }
        result.add(startEdge)

        var steps = 0
        while (!liveEdges.isEmpty() && steps < 5000) {
            val edge = liveEdges.first()
            liveEdges.remove(edge)
            val point: Point? = findPoint(points, edge)
            debug = false
            if (point != null) {
                addLiveEdge(liveEdges, point, edge.start)
                addLiveEdge(liveEdges, edge.end, point)
                result.add(Edge(edge.start, point))
                result.add(Edge(edge.end, point))
            }
            steps++
        }
        println("steps: $steps")
        return result
    }

    private fun addLiveEdge(liveEdges: TreeSet<Edge?>, a: Point, b: Point) {
        val edge = Edge(a, b)
        if (liveEdges.contains(edge)) liveEdges.remove(edge) else liveEdges.add(Edge(b, a))
    }

    private fun findPoint(points: ArrayList<Point>, edge: Edge): Point? {
        var curBestPoint: Point? = null
        var curMax = -1.0
        val edgeBisector: NormalLine = NormalLine.bisector(edge)
        for (point in points) {
            if (Point.area(edge.start, edge.end, point) > 0) {
                val edge1 = Edge(edge.end, point)
                val secondBisector: NormalLine = NormalLine.bisector(edge1)
                val intersection: Point = edgeBisector.intersect(secondBisector)
                val vector = Point(intersection.x - edge.start.x, intersection.y - edge.start.y)
                var curRadius: Double = vector.length()
                if (Point.area(edge.start, edge.end, intersection) < 0) curRadius = -curRadius
                if (curBestPoint == null || curMax > curRadius) {
                    curBestPoint = point
                    curMax = curRadius
                }
            }
        }
        return curBestPoint
    }

    @FXML
    fun canvasClick(event: MouseEvent) {
        val x: Double = event.x
        val y: Double = event.y
        println("Click: $x $y")
        val point = Point(x, y)
        if (rectangleInput) {
            if (firstRectanglePoint == null) {
                firstRectanglePoint = point
            } else {
                rectangle = Rectangle(firstRectanglePoint!!, point)
                firstRectanglePoint = null
                rectangleInputEnded = true
                redraw()
            }
            return
        }
        points.add(point)
        drawPoint(point)
    }

    private fun redraw() {
        canvas?.let { context!!.clearRect(0.0, 0.0, it.width, it.height) }
        context!!.stroke = Color.BLACK
        for (p in points) {
            drawPoint(p)
        }
        context!!.stroke = Color.ROYALBLUE
        if (rectangle != null) rectangle?.draw(context!!)
    }

    @FXML
    fun nextButtonClick() {
        redraw()
        val edges = deloneTriangulation(points)
        for (edge in edges) {
            if (edge != null) {
                context!!.strokeLine(edge.start.x, edge.start.y, edge.end.x, edge.end.y)
            }
        }
        context!!.stroke = Color.BLACK
    }

    private fun drawPoint(point: Point) {
        val x: Double = point.x
        val y: Double = point.y
        context!!.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter)
    }


}