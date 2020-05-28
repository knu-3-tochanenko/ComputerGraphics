class Graph(
    val nodes: MutableList<GraphNode>
) {
    fun addEdge(node1: Int, node2: Int) {
        nodes[node1].addEdge(nodes[node2])
    }

    val edges by lazy {
        val edges: MutableList<Edge> = mutableListOf()
        nodes.toList().forEach { i ->
            i.getEdges().forEach { j ->
                edges.add(Edge(i, j))
            }
        }
        edges.distinctBy { it.from to it.to }
    }

    val edgesDistinct by lazy {
        val edges: MutableList<Edge> = mutableListOf()
        nodes.toList().forEach { i ->
            i.getEdges().forEach { j ->
                if (i.dot.y == j.dot.y) {
                    if (i.dot.x < j.dot.x) {
                        edges.add(Edge(i, j))
                    } else {
                        edges.add(Edge(j, i))
                    }
                } else {
                    if (i.dot.y < j.dot.y) {
                        edges.add(Edge(i, j))
                    } else {
                        edges.add(Edge(j, i))
                    }
                }
            }
        }
        edges.distinctBy { it.from to it.to }
    }

    fun contains(node: GraphNode) = nodes.find { it == node } != null
}