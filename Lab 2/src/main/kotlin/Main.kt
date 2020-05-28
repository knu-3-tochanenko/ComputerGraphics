fun main() {
    val vertexes = arrayListOf(
        GraphNode(Dot(2.0, 0.0)),
        GraphNode(Dot(9.0, 1.0)),
        GraphNode(Dot(1.0, 2.0)),
        GraphNode(Dot(4.0, 2.0)),
        GraphNode(Dot(2.0, 5.0)),
        GraphNode(Dot(6.0, 5.0)),
        GraphNode(Dot(8.0, 5.0)),
        GraphNode(Dot(4.0, 7.0))
    )

    val edges = arrayListOf(
        Pair(0, 2),
        Pair(0, 1),
        Pair(2, 4),
        Pair(2, 3),
        Pair(3, 4),
        Pair(4, 7),
        Pair(5, 7),
        Pair(3, 5),
        Pair(6, 5),
        Pair(1, 3),
        Pair(1, 6)
    )

    val dot = Dot(4.0, 5.0)

    val graph = Graph(vertexes)

    for (edge in edges) {
        graph.addEdge(edge.first, edge.second)
    }

    val chart = Chart()
    chart.addDots(listOf(dot))

    val chains = ChainsMethod(graph, GraphNode(dot))

    for (chain in chains.chains) {
        chart.addLine(vertexListToDotList(chain))
    }

    if (chains.chainsBetween.first.isNotEmpty() && chains.chainsBetween.second.isNotEmpty()) {
        chart.addHighlightedLine(ChartLine(vertexListToDotList(chains.chainsBetween.first)))
        chart.addHighlightedLine(ChartLine(vertexListToDotList(chains.chainsBetween.second)))
    }

    chart.draw()

}

private fun vertexListToDotList(vertexes: List<GraphNode>): List<Dot> {
    val dots = mutableListOf<Dot>()
    for (vertex in vertexes) {
        dots.add(vertex.dot)
    }
    return dots
}