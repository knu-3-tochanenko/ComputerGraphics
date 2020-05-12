import kotlin.math.roundToInt

class HullApproximation {
    companion object {
        private var result = mutableListOf<Dot>()
        private var lines = mutableListOf<ChartLine>()

        fun approximate(dots: List<Dot>, regions: Int): List<Dot> {
            val sides = getSides(dots)
            val sector = (sides.end.x - sides.start.x) / regions

            generateLines(sides, regions, dots)

            val minDots = Array<Dot?>(regions + 1) { null }
            val maxDots = Array<Dot?>(regions + 1) { null }

            var dotSector: Int

            for (dot in dots) {
                dotSector = ((dot.x - sides.start.x) / sector).roundToInt()
                if (maxDots[dotSector] == null || maxDots[dotSector]!!.y > dot.y)
                    maxDots[dotSector] = dot
                if (minDots[dotSector] == null || minDots[dotSector]!!.y < dot.y)
                    minDots[dotSector] = dot
            }

            for (i in 0 until regions) {
                if (minDots[i] != maxDots[i]) {
                    if (minDots[i] != null)
                        result.add(minDots[i]!!)
                    if (maxDots[i] != null)
                        result.add(maxDots[i]!!)
                } else if (minDots[i] != null) {
                    result.add(minDots[i]!!)
                }
            }

            return GiftWrapper.calculate(result)
//            return result
        }

        private fun getSides(dots: List<Dot>): Line {
            val res = Line(dots[0], dots[1])
            for (dot in dots) {
                if (dot.x < res.start.x)
                    res.start = dot
                if (dot.x > res.end.x)
                    res.end = dot
            }
            return res
        }

        private fun generateLines(sides: Line, regions: Int, dots: List<Dot>) {
            var minY = sides.start.y
            var maxY = sides.start.y

            val sector = (sides.end.x - sides.start.x) / regions

            for (dot in dots) {
                if (dot.y > maxY)
                    maxY = dot.y
                if (dot.y < minY)
                    minY = dot.y
            }

            for (i in 0..regions) {
                lines.add(
                    ChartLine(
                        listOf(
                            Dot(i * sector + sides.start.x, minY - 1),
                            Dot(i * sector + sides.start.x, maxY + 1)
                        )
                    )
                )
            }
        }

        fun getLines(): List<ChartLine> {
            return this.lines
        }
    }
}