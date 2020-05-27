import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import java.net.URL
import java.util.*
import kotlin.math.abs
import kotlin.math.sign

class AppController : Initializable {
    @FXML
    var canvas: Canvas? = null

    private var context: GraphicsContext? = null
    private val diameter = 6.0
    private var hull: CircularList<Point?> = CircularList()
    private val points: ArrayList<Point> = ArrayList<Point>()
    private var leftNode: Node<Point?>? = null
    private var rightNode: Node<Point?>? = null


    private fun updateHull(hull: CircularList<Point?>, point: Point) {
        var curPoint: Node<Point?>? = null

        if (hull.size() >= 3) {

            val distLeft: Double = abs(point.x - leftNode!!.data!!.x)
            val distRight: Double = abs(point.x - rightNode!!.data!!.x)
            val startPoint: Node<Point?>

            startPoint = (if (distLeft > distRight) rightNode else leftNode)!!

            var topPoint: Node<Point?> = startPoint
            var bottomPoint: Node<Point?> = startPoint
            var topFound = false
            var bottomFound = false
            var inside: Boolean
            var firstBottomTime = true
            var firstTopTime = true
            var steps = 0

            do {
                steps++

                var a: Double = sign(Point.area(point, topPoint.data!!, topPoint.next!!.data!!))
                var b: Double = sign(Point.area(point, topPoint.data!!, topPoint.prev!!.data!!))

                inside = (!firstTopTime && topPoint === startPoint
                        || !firstBottomTime && bottomPoint === startPoint)

                if (!topFound && (a != b || b <= 0)) {
                    topPoint = topPoint.next!!
                    firstTopTime = false
                } else
                    topFound = true

                a = sign(Point.area(point, bottomPoint.data!!, bottomPoint.next!!.data!!))
                b = sign(Point.area(point, bottomPoint.data!!, bottomPoint.prev!!.data!!))

                if (!bottomFound && (a != b || a >= 0)) {
                    firstBottomTime = false
                    bottomPoint = bottomPoint.prev!!
                } else
                    bottomFound = true
            } while (steps < 5000 && !inside && (!topFound || !bottomFound))

            if (inside)
                return
            if (steps >= 5000) {
                println("Something went wrong, reverting")
                return
            }

            hull.splitNext(bottomPoint, topPoint)
            curPoint = hull.insertAfter(bottomPoint, point)

            context!!.stroke = Color.BLUE
            drawPoint(topPoint.data!!)
            drawPoint(bottomPoint.data!!)
            context!!.stroke = Color.BLACK
        } else if (hull.size() < 3)
            curPoint = hull.insertToEnd(point)

        if (leftNode == null || leftNode!!.data!!.x > point.x)
            leftNode = curPoint
        if (rightNode == null || rightNode!!.data!!.x < point.x)
            rightNode = curPoint
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        context = canvas!!.graphicsContext2D
        onReset()
    }

    @FXML
    fun onReset() {
        context!!.fill = Color.BLACK
        context!!.clearRect(0.0, 0.0, canvas!!.width, canvas!!.height)
    }

    @FXML
    fun canvasClick(event: MouseEvent) {
        redraw()
        val x = event.x
        val y = event.y
        println("Click: $x $y")
        val point = Point(x, y)
        points.add(point)
        updateHull(hull, point)
        redraw()
        drawPoint(point)
    }

    private fun redraw() {
        context!!.clearRect(0.0, 0.0, canvas!!.width, canvas!!.height)
        context!!.stroke = Color.BLACK

        for (p in points) {
            drawPoint(p)
        }

        context!!.stroke = Color.BLUE
        var iter: Node<Point?> = hull.getRoot() ?: return
        if (iter.next == null) return
        val firstP: Point = iter.next!!.data!!
        iter = iter.next!!
        var secondP: Point? = firstP
        var p1: Point?

        do {
            p1 = iter.next!!.data
            iter = iter.next!!
            context!!.strokeLine(secondP!!.x, secondP.y, p1!!.x, p1.y)
            secondP = p1
        } while (iter !== hull.getRoot())

        context!!.strokeLine(firstP.x, firstP.y, secondP!!.x, secondP.y)
        context!!.stroke = Color.BLACK
    }

    private fun drawPoint(point: Point) {
        val x: Double = point.x
        val y: Double = point.y
        context!!.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter)
//        context!!.strokeText("(" + point.x.toString() + "," + point.y.toString() + ")", point.x + 5, point.y + 5)
    }

}