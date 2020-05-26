fun main() {
    val vertexes = arrayListOf(
        GraphNode(Dot(0.0, 0.0)),
        GraphNode(Dot(1.0, 1.5)),
        GraphNode(Dot(5.0, 3.0)),
        GraphNode(Dot(4.0, -1.0)),
        GraphNode(Dot(2.0, 4.0)),
        GraphNode(Dot(3.0, 2.0)),
        GraphNode(Dot(0.0, 3.0)),
        GraphNode(Dot(6.0, 1.0))
    )

    val edges = arrayListOf(
        Pair(0, 1),
        Pair(0, 6),
        Pair(0, 3),
        Pair(6, 1),
        Pair(6, 4),
        Pair(6, 5),
        Pair(3, 1),
        Pair(3, 5),
        Pair(3, 7),
        Pair(1, 5),
        Pair(4, 5),
        Pair(7, 5),
        Pair(4, 2),
        Pair(7, 2),
        Pair(5, 2)
    )

    val dot = Dot(3.0, 10.5)

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