class NormalLine {
    var a = 0.0
    var b = 0.0
    var c = 0.0

    fun getX(y: Double): Double {
        return if (Point.equal(a, 0.0)) 0.0 else -(c + b * y) / a
    }

    private fun getY(x: Double): Double {
        return if (Point.equal(b, 0.0)) 0.0 else -(a * x + c) / b
    }

    fun intersect(line: NormalLine): Point {
        if (Point.equal(b, 0.0) && Point.equal(line.b, 0.0))
            throw RuntimeException("intersect: both b are 0")
        val x: Double
        val y: Double
        when {
            Point.equal(b, 0.0) -> {
                x = -c / a
                y = line.getY(x)
            }
            Point.equal(line.b, 0.0) -> {
                x = -line.c / line.a
                y = getY(x)
            }
            else -> {
                if (Point.equal(line.b * a - line.a * b, 0.0)) {
                    throw RuntimeException("intersect: Cant find intersect: dividing by 0")
                }
                x = (line.c * b - c * line.b) / (line.b * a - line.a * b)
                y = getY(x)
            }
        }
        return Point(x, y)
    }

    companion object {
        fun bisector(a: Edge): NormalLine {
            val midX = (a.end.x + a.start.x) / 2
            val midY = (a.end.y + a.start.y) / 2
            var verticalLine = false
            if (Point.equal(a.end.x - a.start.x, 0.0)) verticalLine = true
            val k = (a.end.y - a.start.y) / (a.end.x - a.start.x)
            val line = NormalLine()
            if (Point.equal(k, 0.0)) {
                line.a = 1.0
                line.b = 0.0
                line.c = -midX
            } else {
                val newK: Double = if (verticalLine) 0.0 else -1 / k
                line.a = -newK
                line.b = 1.0
                line.c = -(midY - newK * midX)
            }
            return line
        }
    }
}