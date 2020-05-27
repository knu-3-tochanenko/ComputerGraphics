import graph.Graph
import graph.Point

fun main() {

    val points: Array<Point?> = arrayOf(
        Point(3.0, -5.0),
        Point(-5.0, 0.0),
        Point(4.0, 1.0),
        Point(1.0, 2.0),
        Point(-1.0, 6.0)
    )

    val matrix = arrayOf(
        booleanArrayOf(false, true, true, true, false),
        booleanArrayOf(true, false, false, true, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, true, false, false, false),
        booleanArrayOf(false, true, true, false, false)
    )

    val graph = Graph(points, matrix)
    graphviz.writeFile("graphviz", graph)
    graphviz.createPNG("graphviz")



    graph.localization(Point(100.0, 100.0))
    graph.localization(Point(4.0, -5.0))
    graph.localization(Point(-100.0, -100.0))
    graph.localization(Point(3.0, 2.0))
}