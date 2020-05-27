import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

class Point(var x: Double, var y: Double) {

    override fun toString(): String {
        return "{" +
                +x +
                ", " + y +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val point = o as Point
        return equal(point.x, x) &&
                equal(point.y, y)
    }

    override fun hashCode(): Int {
        return Objects.hash(x, y)
    }

    companion object {
        fun intersect_1(a: Double, b: Double, c: Double, d: Double): Boolean {
            var a = a
            var b = b
            var c = c
            var d = d
            if (a > b) {
                val temp = a
                a = b
                b = temp
            }
            if (c > d) {
                val temp = c
                c = d
                d = temp
            }
            return max(a, c) <= min(b, d)
        }

        fun area(a: Point, b: Point, c: Point): Double {
            return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)
        }

        fun intersection(a: Point, b: Point, c: Point, d: Point): Boolean {
            return (intersect_1(a.x, b.x, c.x, d.x)
                    && intersect_1(a.y, b.y, c.y, d.y)
                    && sign(area(a, b, c)) * sign(area(a, b, d)) < 0
                    && sign(area(c, d, a)) * sign(area(c, d, b)) < 0
                    )
        }

        fun checkPointInsideSegment(s1: Point, s2: Point, point: Point): Boolean {
            val v1 = Point(point.x - s1.x, point.y - s1.y)
            val v2 = Point(s2.x - point.x, s2.y - point.y)
            return java.lang.Double.compare(v1.x * v2.y - v2.x * v1.y, 0.0) == 0
        }

        fun equal(a: Double, b: Double): Boolean {
            return Math.abs(a - b) < 1e-4
        }

        fun isPointInside(points: ArrayList<Point>, point: Point, minX: Double): Boolean {
            var intersections = 0
            val lineStart = Point(minX, point.y + 0.5)
            for (i in points.indices) {
                val a = points[i]
                if (point.x.compareTo(a.x) == 0 && point.y.compareTo(a.y) == 0)
                    return true
                var b: Point = if (i == points.size - 1) {
                    points[0]
                } else
                    points[i + 1]
                if (checkPointInsideSegment(a, b, point))
                    return true
                if (intersection(a, b, lineStart, point))
                    intersections++
            }
            return intersections % 2 == 1
        }
    }

}