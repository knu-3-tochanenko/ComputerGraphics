import java.lang.Exception
import kotlin.math.abs

class Hull(private val dots: List<Dot>) {
    fun getResult(): List<Dot> {
        if (dots.size < 3) {
            throw Exception("List must contain more than 2 dots")
        }

        var left = 0
        var right = 0

        for (i in dots.indices) {
            if (dots[i].x < dots[left].x)
                left = i
            if (dots[i].x > dots[right].x)
                right = i
        }

        val leftDot = dots[left]
        val rightDot = dots[right]

        val result = mutableListOf<Dot>()
        result.add(leftDot)
        result.add(rightDot)

        val upperDots = mutableListOf<Dot>()
        val lowetDots = mutableListOf<Dot>()

        for (i in dots) {
            if (getSide(leftDot, rightDot, i) == 1) {
                upperDots.add(i)
            } else {
                lowetDots.add(i)
            }
        }

        quickHull(upperDots, result, dots[left], dots[right])
        quickHull(lowetDots, result, dots[right], dots[left])

        return result
    }

    private fun quickHull(
        dots: MutableList<Dot>,
        hull: MutableList<Dot>,
        a: Dot,
        b: Dot
    ) {
        if (dots.isEmpty())
            return
        if (dots.size == 1) {
            hull.add(dots[0])
            return
        }

        val furthest = findFarDot(dots, a, b)
        hull.add(furthest)

        val leftDots = mutableListOf<Dot>()
        val rightDots = mutableListOf<Dot>()

        for (i in dots) {
            if (i == furthest)
                continue
            if (getSide(a, furthest, i) == 1)
                leftDots.add(i)
            else if (getSide(furthest, b, i) == 1)
                rightDots.add(i)
        }

        quickHull(leftDots, hull, a, furthest)
        quickHull(rightDots, hull, furthest, b)
    }

    private fun findFarDot(dots: MutableList<Dot>, a: Dot, b: Dot): Dot {
        var maxDistance = getDistance(dots[0], a, b)
        var maxId = 0
        var distance: Double

        for (i in dots.indices) {
            distance = getDistance(dots[i], a, b)
            if (distance > maxDistance) {
                maxDistance = distance
                maxId = i
            } else if (distance == maxDistance) {
                if (dots[i].x < dots[maxId].x) {
                    maxId = i
                }
            }
        }
        return dots[maxId]
    }

    private fun getSide(a: Dot, b: Dot, c: Dot): Int {
        val distance = getRotation(a, b, c)

        if (distance > 0)
            return 1
        if (distance < 0)
            return -1
        return 0
    }

    private fun getDistance(a: Dot, b: Dot, c: Dot) = abs(getRotation(a, b, c))

    private fun getRotation(a: Dot, b: Dot, c: Dot) = (c.y - a.y) * (b.x - a.x) - (b.y - a.y) * (c.x - a.x)
}