import javafx.scene.canvas.GraphicsContext
import kotlin.math.abs


class Rectangle(
    private var p1: Point,
    private var p2: Point
) {
    fun draw(context: GraphicsContext) {
        val x1 = p1.x.coerceAtMost(p2.x)
        val y1 = p1.y.coerceAtMost(p2.y)
        val w: Double = abs(p1.x - p2.x)
        val h: Double = abs(p1.y - p2.y)
        context.strokeRect(x1, y1, w, h)
    }
}