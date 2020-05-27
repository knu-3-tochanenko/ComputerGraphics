package voronoi

class VoronoiEdge(
    private val site1: Point,
    private val site2: Point
) {
    var m = 0.0
    var b = 0.0
    private val isVertical: Boolean = site1.y == site2.y
    var p1: Point? = null
    var p2: Point? = null
    fun intersection(that: VoronoiEdge): Point? {
        if (m == that.m && b != that.b && isVertical == that.isVertical)
            return null
        val x: Double
        val y: Double
        when {
            isVertical -> {
                x = (site1.x + site2.x) / 2
                y = that.m * x + that.b
            }
            that.isVertical -> {
                x = (that.site1.x + that.site2.x) / 2
                y = m * x + b
            }
            else -> {
                x = (that.b - b) / (m - that.m)
                y = m * x + b
            }
        }
        return Point(x, y)
    }

    init {
        if (isVertical) {
            b = 0.0
            m = b
        } else {
            m = -1.0 / ((site1.y - site2.y) / (site1.x - site2.x))
            val midpoint = Point.midpoint(site1, site2)
            b = midpoint.y - m * midpoint.x
        }
    }
}