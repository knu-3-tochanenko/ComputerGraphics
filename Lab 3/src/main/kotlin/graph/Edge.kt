package graph


class Edge {
    private val a: Vertex
    private val b: Vertex
    private val middle: Point
    private var name: String? = null

    constructor(a: Vertex, b: Vertex) {
        this.a = a
        this.b = b
        middle = Point((a.x + b.x) / 2, (a.y + b.y) / 2)
    }

    constructor(copy: Edge) {
        a = copy.a
        b = copy.b
        middle = copy.middle
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getA(): Vertex {
        return a
    }

    fun getB(): Vertex {
        return b
    }

    val ax: Double
        get() = a.x

    val bx: Double
        get() = b.x

    val ay: Double
        get() = a.y

    val by: Double
        get() = b.y

    fun length(): Double {
        return Math.sqrt(Math.pow(bx - ax, 2.0) + Math.pow(by - ay, 2.0))
    }

    fun xProectionLength(): Double {
        return Math.abs(bx - ax)
    }

    fun pointIsYBetween(point: Point): Boolean {
        if (java.lang.Double.compare(point.y, a.y) <= 0 && java.lang.Double.compare(
                point.y,
                b.y
            ) >= 0 && java.lang.Double.compare(a.y, b.y) >= 0
        ) {
            return true
        }
        return if (java.lang.Double.compare(point.y, a.y) >= 0 && java.lang.Double.compare(
                point.y,
                b.y
            ) <= 0 && java.lang.Double.compare(a.y, b.y) <= 0
        ) {
            true
        } else false
    }

    fun equation(point: Point): Double {
        if (java.lang.Double.compare(a.x, b.x) == 0) {
            return (point.x - a.x).toDouble()
        }
        return (if (java.lang.Double.compare(a.y, b.y) == 0) {
            point.y - a.y
        } else (point.x - a.x) / (b.x - a.x) - (point.y - a.y) / (b.y - a.y)).toDouble()
    }

    fun getXCoordinate(y: Double): Double {
        return ((y - a.y) * (b.x - a.x) / (b.y - a.y) + a.x).toDouble()
    }

    fun middleXInInterval(yMin: Double, yMax: Double): Double {
        val top: Vertex
        val bottom: Vertex
        if (java.lang.Double.compare(a.y, b.y) > 0) {
            top = a
            bottom = b
        } else {
            top = b
            bottom = a
        }
        if (java.lang.Double.compare(top.y, yMin) <= 0 || java.lang.Double.compare(bottom.y, yMax) >= 0) {
            return Double.MAX_VALUE
        }
        val xTop: Double
        val xBottom: Double
        xTop = if (java.lang.Double.compare(top.y, yMax) > 0) {
            getXCoordinate(yMax).toDouble()
        } else {
            top.x
        }
        xBottom = if (java.lang.Double.compare(bottom.y, yMin) < 0) {
            getXCoordinate(yMin).toDouble()
        } else {
            bottom.x
        }
        return ((xTop + xBottom) / 2).toDouble()
    }

    //-1 - left
    //0 - intersect
    // 1 - right
    fun getSide(point: Point): Int {
        val equation = equation(point)
        if (java.lang.Double.compare(equation, 0.0) == 0) {
            //edge is horizontal
            if (java.lang.Double.compare(ay, by) == 0) {
                if (java.lang.Double.compare(point.x, ax) < 0 && java.lang.Double.compare(
                        point.x,
                        bx
                    ) < 0
                ) {
                    return -1
                }
                if (java.lang.Double.compare(point.x, ax) > 0 && java.lang.Double.compare(
                        point.x,
                        bx
                    ) > 0
                ) {
                    return 1
                }
            }
            return 0
        } else if (java.lang.Double.compare(equation, 0.0) > 0) {
            //(1)
            if (java.lang.Double.compare(by, ay) > 0 && java.lang.Double.compare(bx, ax) >= 0) {
                return 1
            }
            if (java.lang.Double.compare(ay, by) > 0 && java.lang.Double.compare(ax, bx) >= 0) {
                return 1
            }
            //(2)
            if (java.lang.Double.compare(by, ay) > 0 && java.lang.Double.compare(bx, ax) <= 0) {
                return -1
            }
            if (java.lang.Double.compare(ay, by) > 0 && java.lang.Double.compare(ax, bx) <= 0) {
                return -1
            }
        } else {
            //(1)
            if (java.lang.Double.compare(by, ay) > 0 && java.lang.Double.compare(bx, ax) >= 0) {
                return -1
            }
            if (java.lang.Double.compare(ay, by) > 0 && java.lang.Double.compare(ax, bx) >= 0) {
                return -1
            }
            //(2)
            if (java.lang.Double.compare(by, ay) > 0 && java.lang.Double.compare(bx, ax) <= 0) {
                return 1
            }
            if (java.lang.Double.compare(ay, by) > 0 && java.lang.Double.compare(ax, bx) <= 0) {
                return 1
            }
        }
        return 0
    }

    fun cos(): Double {
        if (java.lang.Double.compare(b.x, a.x) < 0 && java.lang.Double.compare(b.y, a.y) >= 0) {
            return -xProectionLength() / length()
        }
        if (java.lang.Double.compare(b.x, a.x) > 0 && java.lang.Double.compare(b.y, a.y) > 0) {
            return xProectionLength() / length()
        }
        if (java.lang.Double.compare(b.x, a.x) < 0 && java.lang.Double.compare(b.y, a.y) < 0) {
            return -xProectionLength() / length()
        }
        return (if (java.lang.Double.compare(b.x, a.x) > 0 && java.lang.Double.compare(
                b.y,
                a.y
            ) <= 0
        ) {
            xProectionLength() / length()
        } else 0) as Double
    }

    fun getMiddle(): Point {
        return middle
    }

    override fun toString(): String {
        return " {$name} "
    }
}
