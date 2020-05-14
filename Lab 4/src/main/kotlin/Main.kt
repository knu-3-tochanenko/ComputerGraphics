fun main() {
    val dots = listOf(
        Dot(1.0, 3.0),
        Dot(2.0, 4.0),
        Dot(5.0, 3.0),
        Dot(1.0, 3.0),
        Dot(7.0, 6.5),
        Dot(4.3, 6.1),
        Dot(1.0, 2.5),
        Dot(0.3, 0.0),
        Dot(7.0, 1.5),
        Dot(7.0, 1.5),
        Dot(11.0, 6.0),
        Dot(3.0, 9.0),
        Dot(0.5, 7.0),
        Dot(-0.5, 3.5),
        Dot(5.3, 7.1),
        Dot(1.4,0.5),
        Dot(3.4,5.5),
        Dot(7.0,8.9),
        Dot(0.4,3.3),
        Dot(7.7,4.4),
        Dot(5.0,3.4),
        Dot(4.8,5.1),
        Dot(2.5,3.5),
        Dot(2.6,7.9),
        Dot(4.1, 0.5)
    )

    val rectangle = Rectangle(Dot(3.0, 2.0), Dot(8.5, 6.0))

    val chart = Chart()
    chart.addDots(dots)
    chart.addHighlightedDots(Tree2D.calculate(dots, rectangle))
    chart.addLine(rectangle.toLine())
    chart.draw()
}