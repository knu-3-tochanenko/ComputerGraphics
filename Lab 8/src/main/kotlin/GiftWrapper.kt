class GiftWrapper {
    companion object {
        private var result = mutableListOf<Dot>()

        fun calculate(dots: List<Dot>): List<Dot> {
            assert(dots.size > 2)

            val list = dots.toMutableList()
            val first = mostLeft(dots)
            result.add(dots[first])

            var cur = dots[first]

            do {
                cur = mostRight(cur, list)
                list.remove(cur)
                result.add(cur)
            } while (cur != dots[first])

            return result
        }

        private fun mostLeft(dots: List<Dot>): Int {
            var index = 0;
            for (dot in dots.withIndex()) {
                if (dot.value.x < dots[index].x)
                    index = dot.index
            }
            return index
        }

        private fun mostRight(dot: Dot, dots: List<Dot>): Dot {
            var next = dots[0]
            var index = 0
            for (i in dots.indices) {
                if (getSide(next, dot, dots[i]) == 1) {
                    next = dots[i]
                    index = i
                }
            }
            return dots[index]
        }

        private fun getSide(a: Dot, b: Dot, c: Dot): Int {
            val rotation = getRotation(a, b, c)
            if (rotation > 0)
                return 1
            if (rotation < 0)
                return -1
            return 0
        }

        private fun getRotation(a: Dot, b: Dot, c: Dot): Double {
            return (c.y - a.y) * (b.x - a.x) - (b.y - a.y) * (c.x - a.x)
        }
    }
}