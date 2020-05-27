data class Edge(
    var start: Point,
    var end: Point
) {
    override fun toString(): String {
        return "Edge{" +
                "start=" + start +
                ", end=" + end +
                '}'
    }
}