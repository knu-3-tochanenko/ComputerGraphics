package graph

class Point {
    var x = 0.0
    var y = 0.0

    constructor() {}
    constructor(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    override fun toString(): String {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val point = o as Point
        return java.lang.Double.compare(point.x, x) == 0 &&
                java.lang.Double.compare(point.y, y) == 0
    }
}
