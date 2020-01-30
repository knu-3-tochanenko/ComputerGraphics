class Main {
    companion object {
        var vertexes = mutableListOf<Dot>()
        var dot: Dot

        init {
            vertexes.add(Dot(0.0, 0.0))
            vertexes.add(Dot(0.0, 1.0))
            vertexes.add(Dot(2.0, 2.0))
            vertexes.add(Dot(4.0, 2.0))
            vertexes.add(Dot(3.0, 0.0))
            vertexes.add(Dot(0.5, -1.0))
            dot = Dot(1.1, 1.1)
        }

        fun rotation(a: Dot, b: Dot, c: Dot): Double = ((b.x - a.x) * (c.y - b.y) - (b.y - a.y) * (c.x - b.x))

        fun intersect(a: Dot, b: Dot, c: Dot, d: Dot): Boolean {
            return (rotation(a, b, c) * rotation(a, b, d) <= 0) && (rotation(c, d, a) * rotation(c, d, b) < 0)
        }

        fun checkIfInside(): Boolean {
            val len = vertexes.size;
            println(rotation(vertexes[0], vertexes[1], dot))
            println(rotation(vertexes[0], vertexes[len - 1], dot))
            if ((rotation(vertexes[0], vertexes[1], dot) < 0)
                || (rotation(vertexes[0], vertexes[len - 1], dot) > 0)
            )
                return false

            var l = 1
            var r = len - 1
            var m: Int

            while (l - r > 1) {
                m = (l + r) / 2
                if (rotation(vertexes[0], vertexes[m], dot) < 0) r = m
                else l = m
            }

            return !intersect(vertexes[0], dot, vertexes[l], vertexes[r])
        }

        @JvmStatic
        fun main(args: Array<String>) {
            Visualizer.draw(vertexes, dot)
            Visualizer.setTitle(checkIfInside().toString())
        }
    }
}