class GraphNode(
    var dot: Dot,
    private val edges: MutableList<GraphNode> = mutableListOf()
) {
    var angle = 0.0

    fun addEdge(node: GraphNode) {
        if (edges.find { it == node } == null)
            edges.add(node)
    }

    override fun toString(): String {
        return "(x:${dot.x}, y:${dot.y})"
    }

    fun getEdges() = edges.toList()

    override fun equals(other: Any?): Boolean {
        if (other is GraphNode) {
            if (this.dot.x == other.dot.x && this.dot.y == other.dot.y) {
                return true
            }
        }
        return false
    }
}