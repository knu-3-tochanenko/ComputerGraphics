import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

class Point(val x: Double, val y: Double) : Comparable<Point?> {
    override operator fun compareTo(other: Point?): Int {
        if (other != null) {
            if (x == other.x || java.lang.Double.isNaN(x) && java.lang.Double.isNaN(other.x)) {
                if (y == other.y) {
                    return 0
                }
                return if (y < other.y) -1 else 1
            }
        }
        if (other != null) {
            return if (x < other.x) -1 else 1
        }
        return 0
    }

    override fun toString(): String {
        return String.format("(%.3f, %.3f)", x, y)
    }

    fun distanceTo(that: Point): Double {
        return sqrt((x - that.x) * (x - that.x) + (y - that.y) * (y - that.y))
    }

    override fun equals(other: Any?): Boolean {
        return other is Point && equals(
            other.x,
            x
        ) && equals(other.y, y)
    }

    companion object {
        fun minYOrderedCompareTo(p1: Point, p2: Point): Int {
            if (p1.y < p2.y) return 1
            if (p1.y > p2.y) return -1
            if (p1.x == p2.x) return 0
            return if (p1.x < p2.x) -1 else 1
        }

        fun midpoint(p1: Point, p2: Point): Point {
            val x = (p1.x + p2.x) / 2
            val y = (p1.y + p2.y) / 2
            return Point(x, y)
        }

        fun ccw(a: Point, b: Point, c: Point): Int {
            val area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)
            return if (area2 < 0) -1 else if (area2 > 0) +1 else 0
        }

        private const val EPSILON = 0.0000001
        private fun equals(a: Double, b: Double): Boolean {
            return if (a == b) true else abs(a - b) < EPSILON * max(abs(a), abs(b))
        }
    }

}