package graph

class GVertex : Comparable<GVertex?> {
    private val point: Point

    constructor(point: Point) {
        this.point = point
    }

    constructor(x: Double, y: Double) {
        point = Point(x, y)
    }

    val x: Double
        get() = point.x

    val y: Double
        get() = point.y

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val gVertex = o as GVertex
        return point.equals(gVertex.point)
    }

    override operator fun compareTo(gVertex: GVertex?): Int {
        if (this == gVertex) {
            return 0
        }
        if (java.lang.Double.compare(y, gVertex!!.y) < 0) {
            return -1
        }
        return if (y.compareTo(gVertex!!.y) == 0
            && x.compareTo(gVertex!!.x) > 0) {
            -1
        } else 1
    }

    override fun toString(): String {
        return "{" + point.x.toString() + ", " + point.y.toString() + "}"
    }
}