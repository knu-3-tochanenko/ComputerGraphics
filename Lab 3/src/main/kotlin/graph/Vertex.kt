package graph

class Vertex : Comparable<Vertex?> {
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
        val gVertex = o as Vertex
        return point.equals(gVertex.point)
    }

    override operator fun compareTo(vertex: Vertex?): Int {
        if (this == vertex) {
            return 0
        }
        if (java.lang.Double.compare(y, vertex!!.y) < 0) {
            return -1
        }
        return if (y.compareTo(vertex!!.y) == 0
            && x.compareTo(vertex!!.x) > 0) {
            -1
        } else 1
    }

    override fun toString(): String {
        return "{" + point.x.toString() + ", " + point.y.toString() + "}"
    }
}