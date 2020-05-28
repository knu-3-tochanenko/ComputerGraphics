import java.util.*
import kotlin.math.abs

class Point(var x: Double, var y: Double) {

    override fun toString(): String {
        return "{" +
                +x +
                ", " + y +
                '}'
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val point = other as Point
        return equal(point.x, x) &&
                equal(point.y, y)
    }

    override fun hashCode(): Int {
        return Objects.hash(x, y)
    }

    companion object {

        fun area(a: Point, b: Point, c: Point): Double {
            return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)
        }

        fun equal(a: Double, b: Double): Boolean {
            return abs(a - b) < 1e-4
        }

    }

}