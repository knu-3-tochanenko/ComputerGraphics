package voronoi

import kotlin.math.sqrt

class BreakPoint(
    left: Point,
    right: Point,
    private val e: VoronoiEdge,
    private val isEdgeLeft: Boolean,
    private val v: Voronoi
) {
    val s1: Point = left
    val s2: Point = right
    val edgeBegin: Point?
    private var cacheSweepLoc = 0.0
    private var cachePoint: Point? = null
    fun finish(vert: Point?) {
        if (isEdgeLeft) {
            e.p1 = vert
        } else {
            e.p2 = vert
        }
    }

    fun finish() {
        val p: Point? = point
        if (isEdgeLeft) {
            e.p1 = p
        } else {
            e.p2 = p
        }
    }

    val point: Point?
        get() {
            val l: Double = v.sweepLoc
            if (l == cacheSweepLoc) {
                return cachePoint
            }
            cacheSweepLoc = l
            val x: Double
            val y: Double

            if (s1.y == s2.y) {
                x = (s1.x + s2.x) / 2
                y = (sq(x - s1.x) + sq(s1.y) - sq(l)) / (2 * (s1.y - l))
            } else {
                val px: Double = if (s1.y > s2.y) s1.x else s2.x
                val py: Double = if (s1.y > s2.y) s1.y else s2.y
                val m: Double = e.m
                val b: Double = e.b
                val d = 2 * (py - l)

                val A = 1.0
                val B = -2 * px - d * m
                val C =
                    sq(px) + sq(py) - sq(l) - d * b
                val sign = if (s1.y > s2.y) -1 else 1
                val det = sq(B) - 4 * A * C

                x = if (det <= 0) {
                    -B / (2 * A)
                } else {
                    (-B + sign * sqrt(det)) / (2 * A)
                }
                y = m * x + b
            }
            cachePoint = Point(x, y)
            return cachePoint
        }

    override fun toString(): String {
        return java.lang.String.format("%s \ts1: %s\ts2: %s", point, s1, s2)
    }

    val edge: VoronoiEdge
        get() = e

    companion object {
        private fun sq(d: Double): Double {
            return d * d
        }
    }

    init {
        edgeBegin = point
    }
}