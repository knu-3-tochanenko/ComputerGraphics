fun main() {
    val chart = Chart()
    chart.add(
        Line(
            listOf(
                Dot(1.0, 3.0),
                Dot(5.0, 4.0),
                Dot(5.0, 3.0),
                Dot(1.0, 3.0)
            )
        )
    )
    chart.add(Line(doubleArrayOf(5.0, 4.0, 1.0, 3.0, 5.0), doubleArrayOf(1.0, 4.0, 2.0, 6.0, 1.0)))
    chart.draw()
}