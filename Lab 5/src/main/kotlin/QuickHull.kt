import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2

class QuickHull {
    companion object {
        private var result = mutableListOf<Dot>()

        fun calculate(dots: List<Dot>): List<Dot> {
            assert(dots.size > 2)

            var minX = 0
            var maxX = 0

            // Find most left and right dots
            for (i in dots.indices) {
                if (dots[i].x < dots[minX].x)
                    minX = i
                if (dots[i].x > dots[maxX].x)
                    maxX = i
            }

            quickHull(dots, Line(dots[minX], dots[maxX]), 1)
            quickHull(dots, Line(dots[minX], dots[maxX]), -1)

            sortByAngle()

            return result
        }

        private fun quickHull(dots: List<Dot>, line: Line, side: Int) {
            val furthest = furthestDotIndex(dots, line, side)

            if (furthest == -1) {
                result.add(line.start)
                result.add(line.end)
                return
            }

            quickHull(
                dots,
                Line(dots[furthest], line.start),
                -side(Line(dots[furthest], line.start), line.end)
            )

            quickHull(
                dots,
                Line(dots[furthest], line.end),
                -side(Line(dots[furthest], line.end), line.start)
            )
        }

        private fun furthestDotIndex(dots: List<Dot>, line: Line, side: Int): Int {
            var furthest = -1
            var maxDistance = 0.0

            for (i in dots.indices) {
                val temp = dotToLineDistance(line, dots[i])
                if (side(line, dots[i]) == side && temp > maxDistance) {
                    furthest = i
                    maxDistance = temp
                }
            }

            return furthest
        }

        private fun side(line: Line, dot: Dot): Int {
            val sub = (dot.y - line.start.y) * (line.end.x - line.start.x) -
                    (line.end.y - line.start.y) * (dot.x - line.start.x)

            return when {
                sub == 0.0 -> 0
                sub > 0 -> 1
                else -> -1
            }
        }

        private fun dotToLineDistance(line: Line, dot: Dot): Double {
            return abs(
                (dot.y - line.start.y) * (line.end.x - line.start.x) -
                        (line.end.y - line.start.y) * (dot.x - line.start.x)
            )
        }

        private fun sortByAngle() {
            val center = Dot(0.0, 0.0)
            for (dot in result) {
                center.x += dot.x / result.size
                center.y += dot.y / result.size
            }

            result.map { it.angle = atan2(it.y - center.y, it.x - center.x) * 180 / PI }
            result.sortBy { it.angle }

            result.add(result[0])
        }
    }
}