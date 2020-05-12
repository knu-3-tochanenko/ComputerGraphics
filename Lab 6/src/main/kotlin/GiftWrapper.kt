import kotlin.math.acos
import kotlin.math.sqrt

class GiftWrapper {
    companion object {
        private var result = mutableListOf<Dot>()

        fun calculate(dots: List<Dot>): List<Dot> {
            val list = dots.toMutableList()
            val first = mostLeft(dots)
            list.remove(dots[first])
            result.add(dots[first])

            // Temporary lowest dor for second iteration
            var dot = Dot(dots[first].x, -1000.0)
            var last = dots[first]

            while (dot != dots[first]) {
                dot = mostRight(last, dot, list)
                last = dot
                list.remove(dot)
                result.add(dot)
                if (list.size == 1)
                    break
            }

            return result
        }

        private fun mostLeft(dots: List<Dot>): Int {
            var index = 0;
            for (dot in dots.withIndex()) {
                println(2)
                if (dot.value.x < dots[index].x)
                    index = dot.index
            }
            return index
        }

        private fun mostRight(a: Dot, b: Dot, dots: List<Dot>): Dot {
            val ab = Dot(b.x - a.x, b.y - a.y)
            var lowest = 0
            var lowestAngle = angle(
                ab,
                Dot(dots[0].x - a.x, dots[0].y - a.y)
            )

            for (dot in dots.withIndex()) {
                println("SIZE : ${dots.size}")
                if (angle(ab, Dot(dot.value.x - a.x, dot.value.y - a.y)) < lowestAngle) {
                    lowestAngle = angle(ab, Dot(dot.value.x - a.x, dot.value.y - a.y))
                    lowest = dot.index
                }
            }

            return dots[lowest]
        }

        fun angle(a: Dot, b: Dot): Double = acos(
            (a.x * b.x + a.y * b.y) / (sqrt(a.x * a.x + a.y * a.y) * sqrt(b.x * b.x + b.y * b.y))
        )
    }
}