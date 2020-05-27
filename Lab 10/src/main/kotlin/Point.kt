import java.util.*
import kotlin.math.abs
import kotlin.math.sqrt

class Point(var x: Double, var y: Double) : Comparable<Point?> {

    fun length(): Double {
        return sqrt(x * x + y * y)
    }

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
        private fun intersect(a: Double, b: Double, c: Double, d: Double): Boolean {
            var tmpA = a
            var tmpB = b
            var tmpC = c
            var tmpD = d
            if (tmpA > tmpB) {
                val temp = tmpA
                tmpA = tmpB
                tmpB = temp
            }
            if (tmpC > tmpD) {
                val temp = tmpC
                tmpC = tmpD
                tmpD = temp
            }
            return tmpA.coerceAtLeast(tmpC) <= tmpB.coerceAtMost(tmpD)
        }

        fun area(a: Point, b: Point, c: Point): Double {
            return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)
        }

        fun equal(a: Double, b: Double): Boolean {
            return abs(a - b) < 1e-4
        }
    }

    override fun compareTo(other: Point?): Int {
        if (other == null)
            return 1
        val comp = x.compareTo(other.x)
        return if (comp != 0) comp else y.compareTo(other.y)
    }

}