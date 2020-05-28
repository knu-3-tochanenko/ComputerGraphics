import chart.Chart
import chart.ChartLine
import chart.Dot
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

    val lines = mutableListOf<ChartLine>()

    for (i in matrix[0].indices)
        for (j in matrix[0].indices)
            if (matrix[i][j])
                lines.add(
                    ChartLine(
                        listOf(
                            Dot(points[i]!!.x, points[i]!!.y),
                            Dot(points[j]!!.x, points[j]!!.y)
                        )
                    )
                )


    val dots = arrayListOf(
        Dot(10.0, 10.0),
        Dot(-2.0, 0.0),
        Dot(-10.0, -10.0),
        Dot(2.0, 1.0)
    )

    for (dot in dots) {
        graph.localization(Point(dot.x, dot.y))
    }

    val chart = Chart()
    for (line in lines)
        chart.addLine(line)

    chart.addDots(dots)

    chart.draw()
}